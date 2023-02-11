package eu.joaocosta.minart.runtime

import scala.concurrent.{ExecutionContext, Future}

import eu.joaocosta.minart.audio._
import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.backend.subsystem._
import eu.joaocosta.minart.graphics._
import eu.joaocosta.minart.runtime._

/** App loop that keeps an internal state that is passed to every iteration.
  */
trait AppLoop[State, Subsystem] {

  /** Runs this app loop with a custom loop runner and a set of subsystems.
    *
    * @param runner custom loop runner to use
    * @param createSubsystems operation to create the subsystems
    */
  def run(
      runner: LoopRunner,
      createSubsystems: () => Subsystem
  ): Future[State]

  /** Runs this app loop usinf the default loop runner and subsystems.
    */
  final def run()(implicit
      lr: DefaultBackend[Any, LoopRunner],
      ss: DefaultBackend[Any, Subsystem]
  ): Future[State] =
    run(LoopRunner(), () => ss.defaultValue())
}

object AppLoop {

  /** App loop definition that takes the initial settings, initial state
    * and loop frequency.
    */
  trait Definition[State, Settings, Subsystem] {

    /** Applies the following configuration to the app loop definition
      *
      * @param initialSettings initial settings of the subsystems
      * @param frameRate frame rate of the app loop
      * @param initialState initial state of the loop
      */
    def configure(
        initialSettings: Settings,
        frameRate: LoopFrequency,
        initialState: State
    ): AppLoop[State, Subsystem]

    /** Applies the following configuration to the app loop definition
      *
      * @param initialSettings initial settings of the subsystems
      * @param frameRate frame rate of the app loop
      */
    def configure(
        initialSettings: Settings,
        frameRate: LoopFrequency
    )(implicit ev: Unit =:= State): AppLoop[State, Subsystem] = configure(initialSettings, frameRate, ev(()))
  }

  /** Creates an app loop that keeps and updates a state on every iteration,
    *  terminating when a certain condition is reached.
    *
    *  This is a low level operation for custom subsystems. For most use cases,
    *  [[statefulRenderLoop]], [[statefulAudioLoop]] and [[statefullAppLoop]] are preferred.
    *
    * @param renderFrame operation to render the frame and update the state
    * @param terminateWhen loop termination check
    */

  def statefulLoop[State, Settings, Subsystem <: LowLevelSubsystem[Settings], F[-_, _]](
      renderFrame: State => F[Subsystem, State],
      terminateWhen: State => Boolean = (_: State) => false
  )(implicit effect: FrameEffect[F]): AppLoop.Definition[State, Settings, Subsystem] = {
    new AppLoop.Definition[State, Settings, Subsystem] {
      def configure(
          initialSettings: Settings,
          frameRate: LoopFrequency,
          initialState: State
      ): AppLoop[State, Subsystem] = new AppLoop[State, Subsystem] {
        def run(
            runner: LoopRunner,
            createSubsystem: () => Subsystem
        ): Future[State] = {
          val subsystem = createSubsystem().init(initialSettings)
          runner
            .finiteLoop(
              initialState,
              (state: State) => effect.unsafeRun(renderFrame(state), subsystem),
              (newState: State) => terminateWhen(newState) || !subsystem.isCreated(),
              () => if (subsystem.isCreated()) subsystem.close(),
              frameRate
            )
        }
      }
    }
  }

  /** Creates an app loop that keeps no state.
    *
    *  This is a low level operation for custom subsystems. For most use cases,
    *  [[statelessRenderLoop]], [[statelessAudioLoop]] and [[statelessAppLoop]] are preferred.
    *
    * @param renderFrame operation to render the frame
    * @param frameRate frame rate limit
    */
  def statelessLoop[Settings, Subsystem <: LowLevelSubsystem[Settings], F[-_, _]](
      renderFrame: F[Subsystem, Unit]
  )(implicit effect: FrameEffect[F]): AppLoop.Definition[Unit, Settings, Subsystem] =
    statefulLoop[Unit, Settings, Subsystem, F]((_) => renderFrame)

  /** Creates a render loop with a canvas that keeps and updates a state on every iteration,
    *  terminating when a certain condition is reached.
    *
    * @param renderFrame operation to render the frame and update the state
    * @param terminateWhen loop termination check
    */
  def statefulRenderLoop[State, F[-_, _]](
      renderFrame: State => F[Canvas, State],
      terminateWhen: State => Boolean = (_: State) => false
  )(implicit effect: FrameEffect[F]): AppLoop.Definition[State, Canvas.Settings, LowLevelCanvas] =
    statefulLoop[State, Canvas.Settings, LowLevelCanvas, F](
      renderFrame,
      terminateWhen
    )

  /** Creates a render loop with a canvas that keeps no state.
    *
    * @param renderFrame operation to render the frame
    */
  def statelessRenderLoop[F[-_, _]](
      renderFrame: F[Canvas, Unit]
  )(implicit effect: FrameEffect[F]): AppLoop.Definition[Unit, Canvas.Settings, LowLevelCanvas] =
    statelessLoop[Canvas.Settings, LowLevelCanvas, F](
      renderFrame
    )

  /** Creates an app loop with an audio player that keeps and updates a state on every iteration,
    *  terminating when a certain condition is reached.
    *
    * @param renderFrame operation to render the frame and update the state
    * @param terminateWhen loop termination check
    */
  def statefulAudioLoop[State, F[-_, _]](
      renderFrame: State => F[AudioPlayer, State],
      terminateWhen: State => Boolean = (_: State) => false
  )(implicit effect: FrameEffect[F]): AppLoop.Definition[State, AudioPlayer.Settings, LowLevelAudioPlayer] =
    statefulLoop[State, AudioPlayer.Settings, LowLevelAudioPlayer, F](
      renderFrame,
      terminateWhen
    )

  /** Creates an aoo loop with an audio player that keeps no state.
    *
    * @param renderFrame operation to render the frame
    */
  def statelessAudioLoop[F[-_, _]](
      renderFrame: F[AudioPlayer, Unit]
  )(implicit
      effect: FrameEffect[F]
  ): AppLoop.Definition[Unit, AudioPlayer.Settings, LowLevelAudioPlayer] =
    statelessLoop[AudioPlayer.Settings, LowLevelAudioPlayer, F](
      renderFrame
    )

  type LowLevelAllSubsystems =
    LowLevelSubsystem.Composite[Canvas.Settings, AudioPlayer.Settings, LowLevelCanvas, LowLevelAudioPlayer]

  /** Creates an app loop with a canvas and an audio player that keeps and updates a state on every iteration,
    *  terminating when a certain condition is reached.
    *
    * @param renderFrame operation to render the frame and update the state
    * @param terminateWhen loop termination check
    */
  def statefulAppLoop[State, F[-_, _]](
      renderFrame: State => F[Canvas with AudioPlayer, State],
      terminateWhen: State => Boolean = (_: State) => false
  )(implicit
      effect: FrameEffect[F]
  ): AppLoop.Definition[State, (Canvas.Settings, AudioPlayer.Settings), LowLevelAllSubsystems] =
    statefulLoop[
      State,
      (Canvas.Settings, AudioPlayer.Settings),
      LowLevelAllSubsystems,
      F
    ](
      (state: State) =>
        effect
          .contramap(
            renderFrame(state),
            { case LowLevelSubsystem.Composite(canvas, audioPlayer) => new AllSubsystems(canvas, audioPlayer) }
          ),
      terminateWhen
    )

  /** Creates an app loop with a canvas and an audio player that keeps no state.
    *
    * @param renderFrame operation to render the frame
    */
  def statelessAppLoop[F[-_, _]](
      renderFrame: F[Canvas with AudioPlayer, Unit]
  )(implicit
      effect: FrameEffect[F]
  ): AppLoop.Definition[Unit, (Canvas.Settings, AudioPlayer.Settings), LowLevelAllSubsystems] =
    statelessLoop[(Canvas.Settings, AudioPlayer.Settings), LowLevelAllSubsystems, F](
      effect.contramap(
        renderFrame,
        { case LowLevelSubsystem.Composite(canvas, audioPlayer) => new AllSubsystems(canvas, audioPlayer) }
      )
    )

}
