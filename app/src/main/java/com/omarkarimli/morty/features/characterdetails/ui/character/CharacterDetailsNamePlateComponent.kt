package com.omarkarimli.morty.features.characterdetails.ui.character

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.omarkarimli.morty.R
import com.omarkarimli.morty.core.commonui.CharacterNameComponent
import com.omarkarimli.network.models.domain.CharacterStatus

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CharacterDetailsNamePlateComponent(name: String, status: CharacterStatus) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(8.dp)
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