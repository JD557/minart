package eu.joaocosta.minart.graphics

import scala.concurrent.{ExecutionContext, Future}

import eu.joaocosta.minart.audio._
import eu.joaocosta.minart.backend.defaults._
import eu.joaocosta.minart.backend.subsystem._
import eu.joaocosta.minart.runtime._

/** Render loop that keeps an internal state that is passed to every iteration.
  */
trait RenderLoop[State, Subsystem] {

  /** Runs this render loop with a custom loop runner and a set of subsystems.
    *
    * @param runner custom loop runner to use
    * @param createSubsystems operation to create the subsystems
    */
  def run(
      runner: LoopRunner,
      createSubsystems: () => Subsystem
  ): Future[State]

  /** Runs this render loop usinf the default loop runner and subsystems.
    */
  final def run()(implicit
      lr: DefaultBackend[Any, LoopRunner],
      ss: DefaultBackend[Any, Subsystem]
  ): Future[State] =
    run(LoopRunner(), () => ss.defaultValue())
}

object RenderLoop {

  type LowLevelApp =
    LowLevelSubsystem.Composite[Canvas.Settings, AudioPlayer.Settings, LowLevelCanvas, LowLevelAudioPlayer]

  /** Contains a set of helpful methods to implement basic render
    * loops in a platform agonstic way.
    *
    * @tparam F1 effect type for stateless loops
    * @tparam F2 effect type for stateful loops
    */
  trait Builder[F1[-_, +_], F2[-_, -_, +_]] {

    protected trait FrameEffect[F1[-_, +_], F2[-_, -_, +_]] {
      def contramap[A, AA, B](f: F1[A, B], g: AA => A): F1[AA, B]
      def contramapSubsystem[A, AA, B, C](f: F2[A, B, C], g: AA => A): F2[AA, B, C]
      def addState[A, B](f: F1[A, B]): F2[A, Unit, B]
      def unsafeRun[A, B, C](f: F2[A, B, C], subsystem: A, state: B): C
    }

    protected val effect: FrameEffect[F1, F2]

    /** Creates a render loop that keeps and updates a state on every iteration,
      *  terminating when a certain condition is reached.
      *
      *  This is a low level operation for custom subsystems. For most use cases,
      *  [[statefulRenderLoop]], [[statefulAudioLoop]] and [[statefullAppLoop]] are preferred.
      *
      * @param renderFrame operation to render the frame and update the state
      * @param terminateWhen loop termination check
      */

    def statefulLoop[State, Settings, Subsystem <: LowLevelSubsystem[Settings]](
        renderFrame: F2[Subsystem, State, State],
        terminateWhen: State => Boolean = (_: State) => false
    ): RenderLoop.Definition[State, Settings, Subsystem] = {
      new RenderLoop.Definition[State, Settings, Subsystem] {
        def configure(
            initialSettings: Settings,
            frameRate: LoopFrequency,
            initialState: State
        ): RenderLoop[State, Subsystem] = new RenderLoop[State, Subsystem] {
          def run(
              runner: LoopRunner,
              createSubsystem: () => Subsystem
          ): Future[State] = {
            val subsystem = createSubsystem().init(initialSettings)
            runner
              .finiteLoop(
                (state: State) => effect.unsafeRun(renderFrame, subsystem, state),
                (newState: State) => terminateWhen(newState) || !subsystem.isCreated(),
                frameRate,
                () => if (subsystem.isCreated()) subsystem.close()
              )
              .run(initialState)
          }
        }
      }
    }

    /** Creates a render loop that keeps no state.
      *
      *  This is a low level operation for custom subsystems. For most use cases,
      *  [[statelessRenderLoop]], [[statelessAudioLoop]] and [[statelessAppLoop]] are preferred.
      *
      * @param renderFrame operation to render the frame
      * @param frameRate frame rate limit
      */
    def statelessLoop[Settings, Subsystem <: LowLevelSubsystem[Settings]](
        renderFrame: F1[Subsystem, Unit]
    ): RenderLoop.Definition[Unit, Settings, Subsystem] =
      statefulLoop[Unit, Settings, Subsystem](
        effect.addState(renderFrame)
      )

    /** Creates a render loop with a canvas that keeps and updates a state on every iteration,
      *  terminating when a certain condition is reached.
      *
      * @param renderFrame operation to render the frame and update the state
      * @param terminateWhen loop termination check
      */
    def statefulRenderLoop[State](
        renderFrame: F2[Canvas, State, State],
        terminateWhen: State => Boolean = (_: State) => false
    ): RenderLoop.Definition[State, Canvas.Settings, LowLevelCanvas] =
      statefulLoop[State, Canvas.Settings, LowLevelCanvas](
        renderFrame,
        terminateWhen
      )

    /** Creates a render loop with a canvas that keeps no state.
      *
      * @param renderFrame operation to render the frame
      */
    def statelessRenderLoop(
        renderFrame: F1[Canvas, Unit]
    ): RenderLoop.Definition[Unit, Canvas.Settings, LowLevelCanvas] =
      statelessLoop[Canvas.Settings, LowLevelCanvas](
        renderFrame
      )

    /** Creates a render loop with an audio player that keeps and updates a state on every iteration,
      *  terminating when a certain condition is reached.
      *
      * @param renderFrame operation to render the frame and update the state
      * @param terminateWhen loop termination check
      */
    def statefulAudioLoop[State](
        renderFrame: F2[AudioPlayer, State, State],
        terminateWhen: State => Boolean = (_: State) => false
    ): RenderLoop.Definition[State, AudioPlayer.Settings, LowLevelAudioPlayer] =
      statefulLoop[State, AudioPlayer.Settings, LowLevelAudioPlayer](
        renderFrame,
        terminateWhen
      )

    /** Creates a render loop with an audio playr that keeps no state.
      *
      * @param renderFrame operation to render the frame
      */
    def statelessAudioLoop(
        renderFrame: F1[AudioPlayer, Unit]
    ): RenderLoop.Definition[Unit, AudioPlayer.Settings, LowLevelAudioPlayer] =
      statelessLoop[AudioPlayer.Settings, LowLevelAudioPlayer](
        renderFrame
      )

    /** Creates a render loop with a canvas and an audio player that keeps and updates a state on every iteration,
      *  terminating when a certain condition is reached.
      *
      * @param renderFrame operation to render the frame and update the state
      * @param terminateWhen loop termination check
      */
    def statefulAppLoop[State](
        renderFrame: F2[(Canvas, AudioPlayer), State, State],
        terminateWhen: State => Boolean = (_: State) => false
    ): RenderLoop.Definition[State, (Canvas.Settings, AudioPlayer.Settings), LowLevelApp] =
      statefulLoop[
        State,
        (Canvas.Settings, AudioPlayer.Settings),
        LowLevelApp
      ](
        effect.contramapSubsystem(
          renderFrame,
          { case LowLevelSubsystem.Composite(canvas: LowLevelCanvas, audioPlayer: LowLevelAudioPlayer) =>
            (canvas: Canvas, audioPlayer: AudioPlayer)
          }
        ),
        terminateWhen
      )

    /** Creates a render loop with a canvas and an audio player that keeps no state.
      *
      * @param renderFrame operation to render the frame
      */
    def statelessAppLoop(
        renderFrame: F1[(Canvas, AudioPlayer), Unit]
    ): RenderLoop.Definition[Unit, (Canvas.Settings, AudioPlayer.Settings), LowLevelApp] =
      statelessLoop[(Canvas.Settings, AudioPlayer.Settings), LowLevelApp](
        effect.contramap(
          renderFrame,
          { case LowLevelSubsystem.Composite(canvas: LowLevelCanvas, audioPlayer: LowLevelAudioPlayer) =>
            (canvas: Canvas, audioPlayer: AudioPlayer)
          }
        )
      )
  }

  /** Render loop definition that takes the initial settings, initial state
    * and loop frequency.
    */
  trait Definition[State, Settings, Subsystem] {

    /** Applies the following configuration to the render loop definition
      *
      * @param initialSettings initial settings of the subsystems
      * @param frameRate frame rate of the render loop
      * @param initialState initial state of the loop
      */
    def configure(
        initialSettings: Settings,
        frameRate: LoopFrequency,
        initialState: State
    ): RenderLoop[State, Subsystem]

    /** Applies the following configuration to the render loop definition
      *
      * @param initialSettings initial settings of the subsystems
      * @param frameRate frame rate of the render loop
      */
    def configure(
        initialSettings: Settings,
        frameRate: LoopFrequency
    )(implicit ev: Unit =:= State): RenderLoop[State, Subsystem] = configure(initialSettings, frameRate, ev(()))

  }
}
