package com.App.healtcare.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.App.healtcare.data.local.entity.AppInfoEntity
import com.App.healtcare.data.model.AppItems
import com.google.accompanist.drawablepainter.rememberDrawablePainter

@Composable
fun AppList(
    apps: List<AppItems>,
    onAppCheckedChange: (AppItems, Boolean) -> Unit,
    typeAppSwitch: Boolean,
    iconModeApp: Boolean,
    modifier: Modifier = Modifier
){
    //UI with vertical looks
    if(iconModeApp == false){
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = apps,
                key = {app -> app.packageName}
            ){ app ->
                AppItem(
                    app = app,
                    onCheckedChange = { isChecked ->
                        onAppCheckedChange(app, isChecked)
                    },
                    typeSwitch = typeAppSwitch,
                    iconMode = iconModeApp
                )
            }
        }
    }

    //UI with horizontal looks
    if(iconModeApp){
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 80.dp),
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = apps,
                key = {app -> app.packageName}
            ){ app ->
                AppItem(
                    app = app,
                    onCheckedChange = { isChecked ->
                        onAppCheckedChange(app, isChecked)
                    },
                    typeSwitch = typeAppSwitch,
                    iconMode = iconModeApp
                )
            }
        }
    }

}

@Composable
fun AppItem(
    app: AppItems,
    onCheckedChange: (Boolean) -> Unit,
    typeSwitch: Boolean,
    iconMode: Boolean,
    modifier: Modifier = Modifier
){
    //UI with vertical looks
    if(iconMode == false){
        Row(
            modifier = modifier
                .fillMaxSize()
                .clickable{onCheckedChange(!app.isChecked)}
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            //
            Text(
                text = app.appName,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if(typeSwitch == true){
                Switch(
                    checked = app.isChecked,
                    onCheckedChange = onCheckedChange,
                    modifier = Modifier
                )

            }
        }
    }

    //UI with horizontal looks
    if(iconMode){
        Column(
            modifier = modifier
                .clickable{onCheckedChange(!app.isChecked)}
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberDrawablePainter(drawable = app.icon),
                contentDescription = app.appName,
                modifier = Modifier.size(56.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = app.appName,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}

