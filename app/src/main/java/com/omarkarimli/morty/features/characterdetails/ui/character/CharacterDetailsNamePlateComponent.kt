package com.omarkarimli.morty.features.characterdetails.ui.character

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.omarkarimli.morty.R
import com.omarkarimli.morty.core.commonui.CharacterNameComponent
import com.omarkarimli.network.models.domain.CharacterStatus

@Composable
fun CharacterDetailsNamePlateComponent(name: String, status: CharacterStatus) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CharacterNameComponent(name = name)
        CharacterStatusComponent(characterStatus = status)
    }
}

@Preview
@Composable
fun NamePlatePreviewAlive() {
    CharacterDetailsNamePlateComponent(name = stringResource(R.string.preview_character_name_rick), status = CharacterStatus.Alive)
}

@Preview
@Composable
fun NamePlatePreviewDead() {
    CharacterDetailsNamePlateComponent(name = stringResource(R.string.preview_character_name_rick), status = CharacterStatus.Dead)
}

@Preview
@Composable
fun NamePlatePreviewUnknown() {
    CharacterDetailsNamePlateComponent(name = stringResource(R.string.preview_character_name_rick), status = CharacterStatus.Unknown)
}