import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.cristian.calendarapp.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoleDropDownMenu(
    selectedRole: String,
    onMemberSelected: () -> Unit,
    onModeratorSelected: () -> Unit,
    onAdminSelected: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedRole,
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.role_label)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.member)) },
                onClick = {
                    onMemberSelected()
                    isExpanded = false
                },
                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
            )
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.moderator)) },
                onClick = {
                    onModeratorSelected()
                    isExpanded = false
                },
                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
            )
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.admin)) },
                onClick = {
                    onAdminSelected()
                    isExpanded = false
                },
                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
            )
        }
    }
}