package ru.drvshare.autoshop.mappers.v2.exceptions

import ru.drvshare.autoshop.common.models.EAsCommand

class UnknownAdCommand(command: EAsCommand) : Throwable("Wrong command $command at mapping toTransport stage")
