package com.simplemobiletools.commons.samples.activities

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.simplemobiletools.commons.R
import com.simplemobiletools.commons.compose.alert_dialog.AlertDialogState
import com.simplemobiletools.commons.compose.alert_dialog.rememberAlertDialogState
import com.simplemobiletools.commons.compose.bottom_sheet.BottomSheetDialogState
import com.simplemobiletools.commons.compose.bottom_sheet.rememberBottomSheetDialogState
import com.simplemobiletools.commons.compose.extensions.config
import com.simplemobiletools.commons.compose.extensions.rateStarsRedirectAndThankYou
import com.simplemobiletools.commons.compose.theme.AppThemeSurface
import com.simplemobiletools.commons.dialogs.*
import com.simplemobiletools.commons.extensions.baseConfig
import com.simplemobiletools.commons.extensions.launchUpgradeToProIntent
import com.simplemobiletools.commons.extensions.launchViewIntent
import com.simplemobiletools.commons.extensions.toHex
import com.simplemobiletools.commons.models.FileDirItem
import com.simplemobiletools.commons.models.RadioItem
import com.simplemobiletools.commons.models.Release
import com.simplemobiletools.commons.models.SimpleListItem
import kotlinx.collections.immutable.toImmutableList

class TestDialogActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppThemeSurface {
                MaterialTheme
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Spacer(modifier = Modifier.padding(top = 16.dp))
                    ShowButton(getAppSideLoadedDialogState(), text = "App side loaded dialog")
                    ShowButton(getAddBlockedNumberDialogState(), text = "Add blocked number")
                    ShowButton(getConfirmationAlertDialogState(), text = "Confirmation normal")
                    ShowButton(getConfirmationAdvancedAlertDialogState(), text = "Confirmation advanced")
                    ShowButton(getPermissionRequiredAlertDialogState(), text = "Permission required")
                    ShowButton(getDonateAlertDialogState(), text = "Donate")
                    ShowButton(getFeatureLockedAlertDialogState(), text = "Feature Locked")
                    ShowButton(getPurchaseThankYouAlertDialogState(), text = "Purchase thank you")
                    ShowButton(getLineColorPickerAlertDialogState(), text = "Line color picker")
                    ShowButton(getColorPickerAlertDialogState(), text = "Color picker")
                    ShowButton(getCallConfirmationAlertDialogState(), text = "Call confirmation")
                    ShowButton(getChangeDateTimeFormatAlertDialogState(), text = "Change date time")
                    ShowButton(getRateStarsAlertDialogState(), text = "Rate us")
                    ShowButton(getRadioGroupDialogAlertDialogState(), text = "Radio group")
                    ShowButton(getUpgradeToProAlertDialogState(), text = "Upgrade to pro")
                    ShowButton(getWhatsNewAlertDialogState(), text = "What's new")
                    ShowButton(getChangeViewTypeAlertDialogState(), text = "Change view type")
                    ShowButton(getWritePermissionAlertDialogState(), text = "Write permission dialog")
                    ShowButton(getCreateNewFolderAlertDialogState(), text = "Create new folder")
                    ShowButton(getEnterPasswordAlertDialogState(), text = "Enter password")
                    ShowButton(getFolderLockingNoticeAlertDialogState(), text = "Folder locking notice")
                    ShowButton(getChooserBottomSheetDialogState(), text = "Bottom sheet chooser")
                    ShowButton(getFileConflictAlertDialogState(), text = "File conflict dialog")
                    Spacer(modifier = Modifier.padding(bottom = 16.dp))
                }
            }
        }
    }

    @Composable
    private fun getFileConflictAlertDialogState() = rememberAlertDialogState().apply {
        DialogMember {
            FileConflictAlertDialog(
                alertDialogState = this, fileDirItem = FileDirItem(
                    path = filesDir.path,
                    name = "Test", children = 2
                ), showApplyToAll = false
            ) { resolution, applyForAll ->
                baseConfig.apply {
                    lastConflictApplyToAll = applyForAll
                    lastConflictResolution = resolution
                }
            }
        }
    }

    @Composable
    private fun getChooserBottomSheetDialogState() = rememberBottomSheetDialogState().apply {
        DialogMember {
            val list = remember {
                mutableStateListOf(
                    SimpleListItem(1, R.string.record_video, R.drawable.ic_camera_vector),
                    SimpleListItem(2, R.string.record_audio, R.drawable.ic_microphone_vector, selected = true),
                    SimpleListItem(4, R.string.choose_contact, R.drawable.ic_add_person_vector)
                )
            }
            ChooserBottomSheetDialog(bottomSheetDialogState = this, items = list, onItemClicked = {
                val indexToUpdate = list.indexOf(it)
                list.forEachIndexed { index, simpleListItem ->
                    if (indexToUpdate == index) {
                        list[index] = it.copy(selected = true)
                    } else {
                        list[index] = simpleListItem.copy(selected = false)
                    }
                }
            })
        }
    }

    @Composable
    private fun getFolderLockingNoticeAlertDialogState() = rememberAlertDialogState().apply {
        DialogMember {
            FolderLockingNoticeAlertDialog(alertDialogState = this) {

            }
        }
    }

    @Composable
    private fun getEnterPasswordAlertDialogState() = rememberAlertDialogState().apply {
        DialogMember {
            EnterPasswordAlertDialog(alertDialogState = this, callback = {}, cancelCallback = {})
        }
    }

    @Composable
    private fun getCreateNewFolderAlertDialogState() = rememberAlertDialogState().apply {
        DialogMember {
            CreateNewFolderAlertDialog(this, path = Environment.getExternalStorageDirectory().toString()) {
                //todo create helper for private fun createFolder(path: String, alertDialog: AlertDialog) to extract bundled logic
            }
        }
    }

    @Composable
    private fun getWritePermissionAlertDialogState() = rememberAlertDialogState().apply {
        DialogMember {
            WritePermissionAlertDialog(
                alertDialogState = this,
                writePermissionDialogMode = WritePermissionDialog.WritePermissionDialogMode.OpenDocumentTreeSDK30("."),
                callback = {},
                onCancelCallback = {}
            )
        }
    }

    @Composable
    private fun getChangeViewTypeAlertDialogState() = rememberAlertDialogState().apply {
        DialogMember {
            val currentViewType = config.viewType
            ChangeViewTypeAlertDialog(alertDialogState = this, selectedViewType = currentViewType) { type ->
                Log.d("ChangeViewTypeAlertDialog", type.toString())
                config.viewType = type
            }
        }
    }

    @Composable
    private fun getWhatsNewAlertDialogState(): AlertDialogState = rememberAlertDialogState().apply {
        DialogMember {
            val releases = remember {
                listOf(
                    Release(14, R.string.temporarily_show_excluded),
                    Release(3, R.string.temporarily_show_hidden)
                ).toImmutableList()
            }
            WhatsNewAlertDialog(
                alertDialogState = this, releases = releases
            )
        }
    }

    @Composable
    private fun getUpgradeToProAlertDialogState() = rememberAlertDialogState().apply {
        DialogMember {
            UpgradeToProAlertDialog(alertDialogState = this, onMoreInfoClick = {
                launchViewIntent("https://simplemobiletools.com/upgrade_to_pro")
            }, onUpgradeClick = ::launchUpgradeToProIntent)
        }
    }

    @Composable
    private fun getRadioGroupDialogAlertDialogState() = rememberAlertDialogState().apply {
        DialogMember {
            RadioGroupAlertDialog(
                alertDialogState = this,
                items = listOf(
                    RadioItem(1, "Test"),
                    RadioItem(2, "Test 2"),
                    RadioItem(3, "Test 3"),
                    RadioItem(4, "Test 4"),
                    RadioItem(5, "Test 5"),
                    RadioItem(6, "Test 6"),
                    RadioItem(6, "Test 7"),
                ).toImmutableList(),
                showOKButton = true,
                selectedItemId = 2,
                cancelCallback = {
                    Log.d("getRadioGroupDialogAlertDialogState", "cancelCallback")
                },
                callback = {
                    Log.d("getRadioGroupDialogAlertDialogState", "Selected $it")
                },
                titleId = R.string.title
            )
        }
    }

    @Composable
    private fun getRateStarsAlertDialogState() = rememberAlertDialogState().apply {
        DialogMember {
            RateStarsAlertDialog(alertDialogState = this, onRating = ::rateStarsRedirectAndThankYou)
        }
    }


    @Composable
    private fun getChangeDateTimeFormatAlertDialogState() = rememberAlertDialogState().apply {
        DialogMember {
            ChangeDateTimeFormatAlertDialog(this, is24HourChecked = baseConfig.use24HourFormat) { selectedFormat, is24HourChecked ->
                baseConfig.dateFormat = selectedFormat
                baseConfig.use24HourFormat = is24HourChecked
            }
        }
    }

    @Composable
    private fun getLineColorPickerAlertDialogState() = rememberAlertDialogState().apply {
        DialogMember {
            LineColorPickerAlertDialog(
                alertDialogState = this,
                color = baseConfig.customPrimaryColor,
                isPrimaryColorPicker = true,
                onButtonPressed = { wasPositivePressed, color ->
                    Log.d("getLineColorPickerAlertDialogState", "wasPositivePressed=$wasPositivePressed color=${color.toHex()}")
                }, onActiveColorChange = { color ->
                    Log.d("getLineColorPickerAlertDialogState", "onActiveColorChange=${color.toHex()}")
                })
        }
    }

    @Composable
    private fun getColorPickerAlertDialogState() = rememberAlertDialogState().apply {
        DialogMember {
            ColorPickerAlertDialog(
                alertDialogState = this,
                color = config.customTextColor,
                removeDimmedBackground = true,
                onButtonPressed = { wasPositivePressed, color ->
                    Log.d("getColorPickerAlertDialogState", "wasPositivePressed=$wasPositivePressed color=${color.toHex()}")
                }, onActiveColorChange = { color ->
                    Log.d("getColorPickerAlertDialogState", "onActiveColorChange=${color.toHex()}")
                })
        }
    }

    @Composable
    private fun getPurchaseThankYouAlertDialogState() = rememberAlertDialogState().apply {
        DialogMember {
            PurchaseThankYouAlertDialog(alertDialogState = this)
        }
    }

    @Composable
    private fun getCallConfirmationAlertDialogState() = rememberAlertDialogState().apply {
        DialogMember {
            CallConfirmationAlertDialog(alertDialogState = this, callee = "Simple Mobile Tools", callback = {})
        }
    }

    @Composable
    private fun getFeatureLockedAlertDialogState() = rememberAlertDialogState().apply {
        DialogMember {
            FeatureLockedAlertDialog(alertDialogState = this, cancelCallback = {})
        }
    }

    @Composable
    private fun getDonateAlertDialogState() =
        rememberAlertDialogState().apply {
            DialogMember {
                DonateAlertDialog(alertDialogState = this)
            }
        }


    @Composable
    private fun getConfirmationAlertDialogState() = rememberAlertDialogState().apply {
        DialogMember {
            ConfirmationAlertDialog(alertDialogState = this, callback = {}, dialogTitle = "Some fancy title")
        }
    }

    @Composable
    private fun getAppSideLoadedDialogState() =
        rememberAlertDialogState().apply {
            DialogMember {
                AppSideLoadedAlertDialog(onDownloadClick = {}, onCancelClick = {}, alertDialogState = this)
            }
        }

    @Composable
    private fun getAddBlockedNumberDialogState() =
        rememberAlertDialogState().apply {
            DialogMember {
                AddOrEditBlockedNumberAlertDialog(blockedNumber = null, deleteBlockedNumber = {}, addBlockedNumber = {}, alertDialogState = this)
            }
        }

    @Composable
    private fun getConfirmationAdvancedAlertDialogState() =
        rememberAlertDialogState().apply {
            DialogMember {
                ConfirmationAdvancedAlertDialog(alertDialogState = this, callback = {})
            }
        }

    @Composable
    private fun getPermissionRequiredAlertDialogState() =
        rememberAlertDialogState().apply {
            DialogMember {
                PermissionRequiredAlertDialog(
                    alertDialogState = this,
                    text = "Test permission",
                    positiveActionCallback = {}
                )
            }
        }

    @Composable
    private fun ShowButton(alertDialogState: AlertDialogState, text: String) {
        Button(onClick = alertDialogState::show) {
            Text(
                text = text,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }

    @Composable
    private fun ShowButton(appSideLoadedDialogState: BottomSheetDialogState, text: String) {
        Button(onClick = appSideLoadedDialogState::open) {
            Text(
                text = text,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }


}
