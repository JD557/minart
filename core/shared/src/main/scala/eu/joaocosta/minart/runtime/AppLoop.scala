package eu.joaocosta.minart.runtime

import eu.joaocosta.minart.audio.*
import eu.joaocosta.minart.backend.defaults.*
import eu.joaocosta.minart.backend.subsystem.*
import eu.joaocosta.minart.graphics.*

/** App loop that keeps an internal state that is passed to every iteration.
  */
trait AppLoop[State, Subsystem] {

  /** Runs this app loop with a custom loop runner and a set of subsystems.
    *
    * @param runner custom loop runner to use
    * @param createSubsystems operation to create the subsystems
    */
  def run[F[_]](
      runner: LoopRunner[F],
      createSubsystems: () => Subsystem
  ): F[State]

  /** Runs this app loop using the default loop runner and subsystems.
    */
  final def run[F[_]]()(using
      lr: DefaultBackend[Any, LoopRunner[F]],
      ss: DefaultBackend[Any, Subsystem]
  ): F[State] =
    run(lr.defaultValue(), () => ss.defaultValue())
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
    final def configure(
        initialSettings: Settings,
        frameRate: LoopFrequency
    )(using ev: Unit =:= State): AppLoop[State, Subsystem] = configure(initialSettings, frameRate, ev(()))
  }

  /** Creates an app loop that keeps and updates a state on every iteration,
    *  terminating when a certain condition is reached.
    *
    *  This is a low level operation for custom subsystems. For most use cases,
    *  [[statefulRenderLoop]], [[statefulAudioLoop]] and [[statefulAppLoop]] are preferred.
    *
    * @param renderFrame operation to render the frame and update the state
    * @param terminateWhen loop termination check
    */

  def statefulLoop[State, Settings, Subsystem <: LowLevelSubsystem[Settings]](
      renderFrame: State => Subsystem => State,
      terminateWhen: State => Boolean = (_: State) => false
  ): AppLoop.Definition[State, Settings, Subsystem] = {
    new AppLoop.Definition[State, Settings, Subsystem] {
      def configure(
          initialSettings: Settings,
          frameRate: LoopFrequency,
          initialState: State
      ): AppLoop[State, Subsystem] = new AppLoop[State, Subsystem] {
        def run[F[_]](
            runner: LoopRunner[F],
            createSubsystem: () => Subsystem
        ): F[State] = {
          val subsystem = createSubsystem().init(initialSettings)
          runner
            .finiteLoop(
              initialState,
              (state: State) => renderFrame(state)(subsystem),
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
  def statelessLoop[Settings, Subsystem <: LowLevelSubsystem[Settings]](
      renderFrame: Subsystem => Unit
  ): AppLoop.Definition[Unit, Settings, Subsystem] =
    statefulLoop[Unit, Settings, Subsystem]((_) => renderFrame)

  /** Creates a render loop with a canvas that keeps and updates a state on every iteration,
    *  terminating when a certain condition is reached.
    *
    * @param renderFrame operation to render the frame and update the state
    * @param terminateWhen loop termination check
    */
  def statefulRenderLoop[State](
      renderFrame: State => Canvas => State,
      terminateWhen: State => Boolean = (_: State) => false
  ): AppLoop.Definition[State, Canvas.Settings, LowLevelCanvas] =
    statefulLoop[State, Canvas.Settings, LowLevelCanvas](
      renderFrame,
      terminateWhen
    )

  /** Creates a render loop with a canvas that keeps no state.
    *
    * @param renderFrame operation to render the frame
    */
  def statelessRenderLoop(
      renderFrame: Canvas => Unit
  ): AppLoop.Definition[Unit, Canvas.Settings, LowLevelCanvas] =
    statelessLoop[Canvas.Settings, LowLevelCanvas](
      renderFrame
    )

  /** Creates an app loop with an audio player that keeps and updates a state on every iteration,
    *  terminating when a certain condition is reached.
    *
    * @param renderFrame operation to render the frame and update the state
    * @param terminateWhen loop termination check
    */
  def statefulAudioLoop[State](
      renderFrame: State => AudioPlayer => State,
      terminateWhen: State => Boolean = (_: State) => false
  ): AppLoop.Definition[State, AudioPlayer.Settings, LowLevelAudioPlayer] =
    statefulLoop[State, AudioPlayer.Settings, LowLevelAudioPlayer](
      renderFrame,
      terminateWhen
    )

  /** Creates an aoo loop with an audio player that keeps no state.
    *
    * @param renderFrame operation to render the frame
    */
  def statelessAudioLoop(
      renderFrame: AudioPlayer => Unit
  ): AppLoop.Definition[Unit, AudioPlayer.Settings, LowLevelAudioPlayer] =
    statelessLoop[AudioPlayer.Settings, LowLevelAudioPlayer](
      renderFrame
    )

  /** Creates an app loop with a canvas and an audio player that keeps and updates a state on every iteration,
    *  terminating when a certain condition is reached.
    *
    * @param renderFrame operation to render the frame and update the state
    * @param terminateWhen loop termination check
    */
  def statefulAppLoop[State](
      renderFrame: State => (CanvasSubsystem with AudioPlayerSubsystem) => State,
      terminateWhen: State => Boolean = (_: State) => false
  ): AppLoop.Definition[State, (Canvas.Settings, AudioPlayer.Settings), LowLevelAllSubsystems] =
    statefulLoop[State, (Canvas.Settings, AudioPlayer.Settings), LowLevelAllSubsystems](
      renderFrame,
      terminateWhen
    )

  /** Creates an app loop with a canvas and an audio player that keeps no state.
    *
    * @param renderFrame operation to render the frame
    */
  def statelessAppLoop(
      renderFrame: (CanvasSubsystem with AudioPlayerSubsystem) => Unit
  ): AppLoop.Definition[Unit, (Canvas.Settings, AudioPlayer.Settings), LowLevelAllSubsystems] =
    statelessLoop[(Canvas.Settings, AudioPlayer.Settings), LowLevelAllSubsystems](
      renderFrame
    )

}
