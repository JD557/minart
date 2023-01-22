package eu.joaocosta.minart.backend.subsystem

/** A low-level subsystem with init and close operations.
  */
trait LowLevelSubsystem[Settings] extends AutoCloseable {

  /** Returns the current settings
    */
  def settings: Settings

  /** Checks if the subsystem is created or if it has been destroyed
    */
  def isCreated(): Boolean

  /** Creates the subsystem.
    *
    * Operations can only be called after calling this.
    */
  def init(settings: Settings): Unit

  /** Destroys the subsystem.
    *
    * Calling any operation on this object after calling close() without calling
    * init() has an undefined behavior.
    */
  def close(): Unit
}

object LowLevelSubsystem {

  /** Simple low-level subsystem, with basic settings
    */
  trait Simple[Settings] extends LowLevelSubsystem[Settings] {

    /** Default settings to use when the value is not initialized.
      */
    protected def defaultSettings: Settings

    /** Unsafe implementation of the subsystem init.
      * Should return the new settings
      */
    protected def unsafeInit(settings: Settings): Settings

    /** Unsafe implementation of the subsystem destroy.
      */
    protected def unsafeDestroy(): Unit

    protected var _settings: Settings     = defaultSettings
    private[this] var _isCreated: Boolean = false

    def settings: Settings = _settings

    def isCreated(): Boolean = _isCreated

    def init(settings: Settings): Unit = {
      if (isCreated()) {
        close()
      }
      if (!isCreated()) {
        _settings = unsafeInit(settings)
        _isCreated = true
      }
    }

    def close(): Unit = if (isCreated()) {
      unsafeDestroy()
      _isCreated = false
      _settings = defaultSettings
    }
  }

  /**  Low-level subsystem that keeps an internal extended representation of the settings.
    *
    *  Useful when some common values can be computed during initialization.
    */
  trait Extended[Settings, ExtendedSettings] extends LowLevelSubsystem[Settings] {

    /** Convert the extended settings into settings
      */
    protected def elideSettings(extendedSettings: ExtendedSettings): Settings

    /** Default settings to use when the value is not initialized.
      */
    protected def defaultSettings: ExtendedSettings

    /** Unsafe implementation of the subsystem init.
      * Should return the new extended settings
      */
    protected def unsafeInit(settings: Settings): ExtendedSettings

    /** Unsafe implementation of the subsystem destroy.
      */
    protected def unsafeDestroy(): Unit

    protected var _extendedSettings: ExtendedSettings = defaultSettings
    private[this] var _isCreated: Boolean             = false

    def settings: Settings                 = elideSettings(_extendedSettings)
    def extendedSettings: ExtendedSettings = _extendedSettings

    def isCreated(): Boolean = _isCreated

    def init(settings: Settings): Unit = {
      if (isCreated()) {
        close()
      }
      if (!isCreated()) {
        _extendedSettings = unsafeInit(settings)
        _isCreated = true
      }
    }

    def close(): Unit = if (isCreated()) {
      unsafeDestroy()
      _isCreated = false
      _extendedSettings = defaultSettings
    }
  }
}
