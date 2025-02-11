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

package androidx.compose.ui.text

import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SaversTest {
    private val defaultSaverScope = SaverScope { true }

    @Test
    fun test_TextUnit() {
        val original = 2.sp
        val saved = with(TextUnit.Saver) { defaultSaverScope.save(original) }

        assertThat(TextUnit.Saver.restore(saved!!)).isEqualTo(original)
    }

    @Test
    fun test_TextUnit_unspecified() {
        val original = TextUnit.Unspecified
        val saved = with(TextUnit.Saver) { defaultSaverScope.save(original) }
        val restored = TextUnit.Saver.restore(saved!!)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_Offset() {
        val original = Offset(10f, 10f)
        val saved = with(Offset.Saver) { defaultSaverScope.save(original) }

        assertThat(Offset.Saver.restore(saved!!)).isEqualTo(original)
    }

    @Test
    fun test_Offset_Unspecified() {
        val original = Offset.Unspecified
        val saved = with(Offset.Saver) { defaultSaverScope.save(original) }
        val restored = Offset.Saver.restore(saved!!)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_Offset_Infinite() {
        val original = Offset.Infinite
        val saved = with(Offset.Saver) { defaultSaverScope.save(original) }
        val restored = Offset.Saver.restore(saved!!)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_Color() {
        val original = Color.Yellow
        val saved = with(Color.Saver) { defaultSaverScope.save(original) }

        assertThat(Color.Saver.restore(saved!!)).isEqualTo(original)
    }

    @Test
    fun test_Color_Unspecified() {
        val original = Color.Unspecified
        val saved = with(Color.Saver) { defaultSaverScope.save(original) }
        val restored = Color.Saver.restore(saved!!)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_Shadow() {
        val original = Shadow(color = Color.Blue, offset = Offset(5f, 5f), blurRadius = 2f)
        val saved = with(Shadow.Saver) { defaultSaverScope.save(original) }
        val restored = Shadow.Saver.restore(saved!!)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_Shadow_None() {
        val original = Shadow.None
        val saved = with(Shadow.Saver) { defaultSaverScope.save(original) }
        val restored = Shadow.Saver.restore(saved!!)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_ParagraphStyle() {
        val original = ParagraphStyle()
        val saved = with(ParagraphStyleSaver) { defaultSaverScope.save(original) }
        val restored = ParagraphStyleSaver.restore(saved!!)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_ParagraphStyle_with_a_nonnull_value() {
        val original = ParagraphStyle(textDirection = TextDirection.Rtl)
        val saved = with(ParagraphStyleSaver) { defaultSaverScope.save(original) }
        val restored = ParagraphStyleSaver.restore(saved!!)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_ParagraphStyle_with_no_null_value() {
        val original = ParagraphStyle(
            textAlign = TextAlign.Justify,
            textDirection = TextDirection.Rtl,
            lineHeight = 10.sp,
            textIndent = TextIndent(firstLine = 2.sp, restLine = 3.sp)
        )
        val saved = with(ParagraphStyleSaver) { defaultSaverScope.save(original) }
        val restored = ParagraphStyleSaver.restore(saved!!)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_SpanStyle() {
        val original = SpanStyle()
        val saved = with(SpanStyleSaver) { defaultSaverScope.save(original) }
        val restored = SpanStyleSaver.restore(saved!!)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_SpanStyle_with_a_nonnull_value() {
        val original = SpanStyle(baselineShift = BaselineShift.Subscript)
        val saved = with(SpanStyleSaver) { defaultSaverScope.save(original) }
        val restored = SpanStyleSaver.restore(saved!!)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_SpanStyle_with_no_null_value() {
        val original = SpanStyle(
            color = Color.Red,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontSynthesis = FontSynthesis.All,
            // fontFamily =
            fontFeatureSettings = "feature settings",
            letterSpacing = 2.em,
            baselineShift = BaselineShift.Superscript,
            textGeometricTransform = TextGeometricTransform(2f, 3f),
            // localeList =
            background = Color.Blue,
            textDecoration = TextDecoration.LineThrough,
            shadow = Shadow(color = Color.Red, offset = Offset(2f, 2f), blurRadius = 4f)
        )
        val saved = with(SpanStyleSaver) { defaultSaverScope.save(original) }
        val restored = SpanStyleSaver.restore(saved!!)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_FontWeight() {
        val original = FontWeight(123)
        val saved = with(FontWeight.Saver) { defaultSaverScope.save(original) }

        assertThat(FontWeight.Saver.restore(saved!!)).isEqualTo(original)
    }

    @Test
    fun test_FontWeight_w100() {
        val original = FontWeight.W100
        val saved = with(FontWeight.Saver) { defaultSaverScope.save(original) }

        val restored = FontWeight.Saver.restore(saved!!)
        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_BaselineShift() {
        val original = BaselineShift(2f)
        val saved = with(BaselineShift.Saver) { defaultSaverScope.save(original) }

        assertThat(BaselineShift.Saver.restore(saved!!)).isEqualTo(original)
    }

    @Test
    fun test_BaselineShift_None() {
        val original = BaselineShift.None
        val saved = with(BaselineShift.Saver) { defaultSaverScope.save(original) }
        val restored = BaselineShift.Saver.restore(saved!!)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_TextDecoration() {
        val original = TextDecoration.combine(
            listOf(TextDecoration.LineThrough, TextDecoration.Underline)
        )
        val saved = with(TextDecoration.Saver) { defaultSaverScope.save(original) }

        assertThat(TextDecoration.Saver.restore(saved!!)).isEqualTo(original)
    }

    @Test
    fun test_TextDecoration_None() {
        val original = TextDecoration.None
        val saved = with(TextDecoration.Saver) { defaultSaverScope.save(original) }

        val restored = TextDecoration.Saver.restore(saved!!)
        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun testSaveRestore_lineThrough() {
        val original = TextDecoration.LineThrough
        val saved = with(TextDecoration.Saver) { defaultSaverScope.save(original) }
        val restored = TextDecoration.Saver.restore(saved!!)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun testSaveRestore_underline() {
        val original = TextDecoration.Underline
        val saved = with(TextDecoration.Saver) { defaultSaverScope.save(original) }
        val restored = TextDecoration.Saver.restore(saved!!)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_TextGeometricTransform() {
        val original = TextGeometricTransform(1f, 2f)
        val saved = with(TextGeometricTransform.Saver) { defaultSaverScope.save(original) }

        assertThat(TextGeometricTransform.Saver.restore(saved!!)).isEqualTo(original)
    }

    @Test
    fun test_TextGeometricTransform_None() {
        val original = TextGeometricTransform.None
        val saved = with(TextGeometricTransform.Saver) { defaultSaverScope.save(original) }
        val restored = TextGeometricTransform.Saver.restore(saved!!)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_TextIndent() {
        val original = TextIndent(1.sp, 2.sp)
        val saved = with(TextIndent.Saver) { defaultSaverScope.save(original) }

        assertThat(TextIndent.Saver.restore(saved!!)).isEqualTo(original)
    }

    @Test
    fun test_TextIndent_None() {
        val original = TextIndent.None
        val saved = with(TextIndent.Saver) { defaultSaverScope.save(original) }
        val restored = TextIndent.Saver.restore(saved!!)

        assertThat(restored).isEqualTo(original)
    }
}