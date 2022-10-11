package ru.drvshare.autoshop.mappers.v2.exceptions

import ru.drvshare.autoshop.common.models.EAdCommand

class UnknownAdCommand(command: EAdCommand) : Throwable("Wrong command $command at mapping toTransport stage")
