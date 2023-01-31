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

  /** Helper type for a subsystem composed by a Canvas and an AudioPlayer */
  type LowLevelApp =
    LowLevelSubsystem.Composite[Canvas.Settings, AudioPlayer.Settings, LowLevelCanvas, LowLevelAudioPlayer]

  /** Typeclass for effect types for computations that run on each frame.
    *
    * `F1[Subsystem, NewState]` represents a computation that takes a subsystem and returns a new state.
    * `F2[Subsystem, OldState, NewState]` represents a computation that takes a subsystem and an old state returns a
    * new state.
    */
  trait FrameEffect[F1[-_, +_], F2[-_, -_, +_]] {
    def contramap[A, AA, B](f: F1[A, B], g: AA => A): F1[AA, B]
    def contramapSubsystem[A, AA, B, C](f: F2[A, B, C], g: AA => A): F2[AA, B, C]
    def addState[A, B](f: F1[A, B]): F2[A, Unit, B]
    def unsafeRun[A, B, C](f: F2[A, B, C], subsystem: A, state: B): C
  }

  object FrameEffect {
    implicit val functionFrameEffect: FrameEffect[Function1, Function2] = new FrameEffect[Function1, Function2] {
      def contramap[A, AA, B](f: A => B, g: AA => A): AA => B =
        g.andThen(f)
      def contramapSubsystem[A, AA, B, C](f: (A, B) => C, g: AA => A): (AA, B) => C =
        (subsystem: AA, state: B) => f(g(subsystem), state)
      def addState[A, B](f: A => B): (A, Unit) => B =
        (subsystem: A, _: Unit) => f(subsystem)
      def unsafeRun[A, B, C](f: (A, B) => C, subsystem: A, state: B): C =
        f(subsystem, state)
    }
  }

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

  def statefulLoop[State, Settings, Subsystem <: LowLevelSubsystem[Settings], F1[-_, +_], F2[-_, -_, +_]](
      renderFrame: F2[Subsystem, State, State],
      terminateWhen: State => Boolean = (_: State) => false
  )(implicit effect: FrameEffect[F1, F2]): AppLoop.Definition[State, Settings, Subsystem] = {
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
              (state: State) => effect.unsafeRun(renderFrame, subsystem, state),
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
  def statelessLoop[Settings, Subsystem <: LowLevelSubsystem[Settings], F1[-_, +_], F2[-_, -_, +_]](
      renderFrame: F1[Subsystem, Unit]
  )(implicit effect: FrameEffect[F1, F2]): AppLoop.Definition[Unit, Settings, Subsystem] =
    statefulLoop[Unit, Settings, Subsystem, F1, F2](
      effect.addState(renderFrame)
    )

  /** Creates a render loop with a canvas that keeps and updates a state on every iteration,
    *  terminating when a certain condition is reached.
    *
    * @param renderFrame operation to render the frame and update the state
    * @param terminateWhen loop termination check
    */
  def statefulRenderLoop[State, F1[-_, +_], F2[-_, -_, +_]](
      renderFrame: F2[Canvas, State, State],
      terminateWhen: State => Boolean = (_: State) => false
  )(implicit effect: FrameEffect[F1, F2]): AppLoop.Definition[State, Canvas.Settings, LowLevelCanvas] =
    statefulLoop[State, Canvas.Settings, LowLevelCanvas, F1, F2](
      renderFrame,
      terminateWhen
    )

  /** Creates a render loop with a canvas that keeps no state.
    *
    * @param renderFrame operation to render the frame
    */
  def statelessRenderLoop[F1[-_, +_], F2[-_, -_, +_]](
      renderFrame: F1[Canvas, Unit]
  )(implicit effect: FrameEffect[F1, F2]): AppLoop.Definition[Unit, Canvas.Settings, LowLevelCanvas] =
    statelessLoop[Canvas.Settings, LowLevelCanvas, F1, F2](
      renderFrame
    )

  /** Creates an app loop with an audio player that keeps and updates a state on every iteration,
    *  terminating when a certain condition is reached.
    *
    * @param renderFrame operation to render the frame and update the state
    * @param terminateWhen loop termination check
    */
  def statefulAudioLoop[State, F1[-_, +_], F2[-_, -_, +_]](
      renderFrame: F2[AudioPlayer, State, State],
      terminateWhen: State => Boolean = (_: State) => false
  )(implicit effect: FrameEffect[F1, F2]): AppLoop.Definition[State, AudioPlayer.Settings, LowLevelAudioPlayer] =
    statefulLoop[State, AudioPlayer.Settings, LowLevelAudioPlayer, F1, F2](
      renderFrame,
      terminateWhen
    )

  /** Creates an aoo loop with an audio player that keeps no state.
    *
    * @param renderFrame operation to render the frame
    */
  def statelessAudioLoop[F1[-_, +_], F2[-_, -_, +_]](
      renderFrame: F1[AudioPlayer, Unit]
  )(implicit effect: FrameEffect[F1, F2]): AppLoop.Definition[Unit, AudioPlayer.Settings, LowLevelAudioPlayer] =
    statelessLoop[AudioPlayer.Settings, LowLevelAudioPlayer, F1, F2](
      renderFrame
    )

  /** Creates an app loop with a canvas and an audio player that keeps and updates a state on every iteration,
    *  terminating when a certain condition is reached.
    *
    * @param renderFrame operation to render the frame and update the state
    * @param terminateWhen loop termination check
    */
  def statefulAppLoop[State, F1[-_, +_], F2[-_, -_, +_]](
      renderFrame: F2[(Canvas, AudioPlayer), State, State],
      terminateWhen: State => Boolean = (_: State) => false
  )(implicit
      effect: FrameEffect[F1, F2]
  ): AppLoop.Definition[State, (Canvas.Settings, AudioPlayer.Settings), LowLevelApp] =
    statefulLoop[
      State,
      (Canvas.Settings, AudioPlayer.Settings),
      LowLevelApp,
      F1,
      F2
    ](
      effect.contramapSubsystem(
        renderFrame,
        { case LowLevelSubsystem.Composite(canvas: LowLevelCanvas, audioPlayer: LowLevelAudioPlayer) =>
          (canvas: Canvas, audioPlayer: AudioPlayer)
        }
      ),
      terminateWhen
    )

  /** Creates an app loop with a canvas and an audio player that keeps no state.
    *
    * @param renderFrame operation to render the frame
    */
  def statelessAppLoop[F1[-_, +_], F2[-_, -_, +_]](
      renderFrame: F1[(Canvas, AudioPlayer), Unit]
  )(implicit
      effect: FrameEffect[F1, F2]
  ): AppLoop.Definition[Unit, (Canvas.Settings, AudioPlayer.Settings), LowLevelApp] =
    statelessLoop[(Canvas.Settings, AudioPlayer.Settings), LowLevelApp, F1, F2](
      effect.contramap(
        renderFrame,
        { case LowLevelSubsystem.Composite(canvas: LowLevelCanvas, audioPlayer: LowLevelAudioPlayer) =>
          (canvas: Canvas, audioPlayer: AudioPlayer)
        }
      )
    )

}
