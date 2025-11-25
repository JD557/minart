package eu.joaocosta.minart.backend.subsystem

type Settings[Subsystem] = Subsystem match {
  case LowLevelSubsystem[settings] => settings
}
