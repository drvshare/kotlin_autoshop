package ru.drvshare.autoshop.mappers.v1.exceptions

import ru.drvshare.autoshop.common.models.EAdCommand


class UnknownAdCommand(command: EAdCommand) : Throwable("Wrong command $command at mapping toTransport stage")
