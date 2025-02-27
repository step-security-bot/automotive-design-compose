/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.designcompose.testapp.validation.examples

import android.annotation.TargetApi
import androidx.compose.runtime.Composable
import com.android.designcompose.benchmarks.battleship.lib.BattleshipDoc
import com.android.designcompose.benchmarks.battleship.lib.BattleshipTest
import java.time.Clock

val EXAMPLES: ArrayList<Triple<String, @Composable () -> Unit, String?>> =
    arrayListOf(
        Triple("Hello", { HelloWorld() }, HelloWorldDoc.javaClass.name),
        Triple("HelloVersion", { HelloVersion() }, HelloVersionDoc.javaClass.name),
        Triple("Image Update", { ImageUpdateTest() }, ImageUpdateTestDoc.javaClass.name),
        Triple("Telltales", { TelltaleTest() }, TelltaleTestDoc.javaClass.name),
        Triple("Variant *", { VariantAsteriskTest() }, VariantAsteriskTestDoc.javaClass.name),
        Triple("Alignment", { AlignmentTest() }, AlignmentTestDoc.javaClass.name),
        Triple("Battleship", { BattleshipTest() }, BattleshipDoc.javaClass.name),
        Triple("H Constraints", { HConstraintsTest() }, ConstraintsDoc.javaClass.name),
        Triple("V Constraints", { VConstraintsTest() }, ConstraintsDoc.javaClass.name),
        Triple("Interaction", { InteractionTest() }, InteractionTestDoc.javaClass.name),
        Triple("Shadows", { ShadowsTest() }, ShadowsTestDoc.javaClass.name),
        Triple(
            "Variant Properties",
            { VariantPropertiesTest() },
            VariantPropertiesTestDoc.javaClass.name
        ),
        Triple("1px Separator", { OnePxSeparatorTest() }, OnePxSeparatorDoc.javaClass.name),
        Triple("Text Elide", { TextElideTest() }, TextElideTestDoc.javaClass.name),
        Triple("Styled Text Runs", { StyledTextRunsTest() }, StyledTextRunsDoc.javaClass.name),
        Triple("Fancy Fills", { FancyFillTest() }, FancyFillTestDoc.javaClass.name),
        Triple("Fill Container", { FillTest() }, FillTestDoc.javaClass.name),
        Triple(
            "Grid Layout Documentation",
            { GridLayoutDocumentation() },
            GridLayoutDoc.javaClass.name
        ),
        Triple("Blend Modes", { BlendModeTest() }, BlendModeTestDoc.javaClass.name),
        Triple(
            "Vector Rendering",
            { VectorRenderingTest() },
            VectorRenderingTestDoc.javaClass.name
        ),
        Triple("Dials Gauges", { DialsGaugesTest() }, DialsGaugesTestDoc.javaClass.name),
        Triple("Masks", { MaskTest() }, MaskTestDoc.javaClass.name),
        Triple("Variable Borders", { VariableBorderTest() }, VariableBorderTestDoc.javaClass.name),
        Triple("Custom Brush", { CustomBrushTest() }, CustomBrushTestDoc.javaClass.name),
        Triple("Component Replace", { ComponentReplaceTest() }, ComponentReplaceDoc.javaClass.name),
        // Don't run in CI, need an annotation.
        // Triple("Compositing", { CompositingViewsTest() },
        // CompositingViewsTestDoc.javaClass.name),
        Triple("Text Inval", { TextResizingTest() }, TextResizingTestDoc.javaClass.name),
        Triple("Variable Modes", { VariableModesTest() }, VariablesTestDoc.javaClass.name),
        Triple(
            "State Customizations",
            @TargetApi(28) { StateCustomizationsTest(Clock.systemDefaultZone()) },
            StateCustomizationsDoc.javaClass.name
        ),
        // GH-636: Test takes too long to execute.
        // Triple("Very large File", { VeryLargeFile() }, VeryLargeFileDoc.javaClass.name)
    )

// Default renderer doesn't support animations
val SQUOOSH_ONLY_EXAMPLES: ArrayList<Triple<String, @Composable () -> Unit, String?>> =
    arrayListOf(
        Triple("SA", { SmartAnimateTest() }, SmartAnimateTestDoc.javaClass.name),
        Triple("SA Variant", { VariantAnimationTest() }, VariantAnimationTestDoc.javaClass.name),
    )

val DEFAULT_RENDERER_ONLY_EXAMPLES: ArrayList<Triple<String, @Composable () -> Unit, String?>> =
    arrayListOf(
        // Squoosh doesn't work with the doc id override.
        Triple("HelloBye", { HelloBye() }, HelloByeDoc.javaClass.name),
        // Squoosh doesn't work with ImageReplacementContext
        Triple("Color Tint", { ColorTintTest() }, ColorTintTestDoc.javaClass.name),

        // Lazy Grid doesn't actually use a doc
        // This example is not using any of the renderers.
        Triple("Lazy Grid", { LazyGridItemSpans() }, null),
        // Squoosh doesn't work with ListContent
        Triple("Grid Layout", { GridLayoutTest() }, GridLayoutTestDoc.javaClass.name),
        Triple("Grid Widget", { GridWidgetTest() }, GridWidgetTestDoc.javaClass.name),
        Triple("List Widget", { ListWidgetTest() }, ListWidgetTestDoc.javaClass.name),
        // Squoosh doesn't work with ReplacementContent
        Triple("CrossAxis Fill", { CrossAxisFillTest() }, CrossAxisFillTestDoc.javaClass.name),
        Triple("Item Spacing", { ItemSpacingTest() }, ItemSpacingTestDoc.javaClass.name),
        Triple("Layout Tests", { LayoutTests() }, LayoutTestsDoc.javaClass.name),
        Triple(
            "Layout Replacement",
            { LayoutReplacementTest() },
            LayoutReplacementTestDoc.javaClass.name
        ),
        Triple("OpenLink", { OpenLinkTest() }, OpenLinkTestDoc.javaClass.name),
        Triple(
            "Recurse Customization",
            { RecursiveCustomizations() },
            RecursiveCustomizationsDoc.javaClass.name
        ),
        Triple("Shared Customization", { ModuleExample() }, ModuleExampleDoc.javaClass.name),
        Triple(
            "Variant Interactions",
            { VariantInteractionsTest() },
            VariantInteractionsTestDoc.javaClass.name
        ),
    )
