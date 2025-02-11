/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.compose.foundation.text

import android.os.Build
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.ImeAction.Default
import androidx.compose.ui.text.input.ImeAction.None
import androidx.compose.ui.text.input.ImeAction.Go
import androidx.compose.ui.text.input.ImeAction.Search
import androidx.compose.ui.text.input.ImeAction.Send
import androidx.compose.ui.text.input.ImeAction.Previous
import androidx.compose.ui.text.input.ImeAction.Next
import androidx.compose.ui.text.input.ImeAction.Done
import androidx.compose.ui.text.input.ImeOptions
import androidx.compose.ui.text.input.TextFieldValue
import com.google.common.truth.Truth.assertThat
import androidx.test.filters.LargeTest
import androidx.test.filters.SdkSuppress
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@LargeTest
@RunWith(Parameterized::class)
class KeyboardActionsTest(private val imeAction: ImeAction) {
    @get:Rule
    val rule = createComposeRule()

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "ImeAction = {0}")
        fun initParameters() = ImeAction.values()
            .filter { it != Default && it != None } // OS never shows a Default or None ImeAction.
    }

    @Test
    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.R)
    fun performingKeyboardAction_triggersCorrectCallback() {
        // Arrange.
        val initialTextField = "text field test tag"
        val value = TextFieldValue("Placeholder Text")
        val actionTriggerLog = mutableMapOf<ImeAction, Boolean>().withDefault { false }

        rule.setContent {
            CoreTextField(
                value = value,
                onValueChange = {},
                modifier = Modifier.testTag(initialTextField),
                imeOptions = ImeOptions(imeAction = imeAction),
                keyboardActions = KeyboardActions(
                    onDone = { actionTriggerLog[Done] = true },
                    onGo = { actionTriggerLog[Go] = true },
                    onNext = { actionTriggerLog[Next] = true },
                    onPrevious = { actionTriggerLog[Previous] = true },
                    onSearch = { actionTriggerLog[Search] = true },
                    onSend = { actionTriggerLog[Send] = true }
                )
            )
        }

        // Act.
        rule.onNodeWithTag(initialTextField).performImeAction()

        // Assert.
        actionTriggerLog.forEach { (action, triggered) ->
            when (action) {
                imeAction -> assertThat(triggered).isTrue()
                else -> assertThat(triggered).isFalse()
            }
        }
    }
}
