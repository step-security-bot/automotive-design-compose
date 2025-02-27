/*
 * Copyright 2024 Google LLC
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

use crate::legacy_definition::element::background::Background;
use serde::{Deserialize, Serialize};

#[derive(Clone, Copy, Debug, PartialEq, Deserialize, Serialize)]
pub enum LineHeight {
    Pixels(f32),
    Percent(f32),
}

impl Default for LineHeight {
    fn default() -> Self {
        LineHeight::Percent(1.0)
    }
}

/// How is a stroke aligned to its containing box?
#[derive(Clone, Copy, Debug, PartialEq, Deserialize, Serialize)]
pub enum StrokeAlign {
    /// The stroke is entirely within the containing view. The stroke's outer edge matches the
    /// outer edge of the containing view.
    Inside,
    /// The stroke is centered on the edge of the containing view, and extends into the view
    /// on the inside, and out of the view on the outside.
    Center,
    /// The stroke is entirely outside of the view. The stroke's inner edge is the outer edge
    /// of the containing view.
    Outside,
}

/// Stroke weight is either a uniform value for all sides, or individual
/// weights for each side.
#[derive(Clone, Copy, Debug, PartialEq, Deserialize, Serialize)]
pub enum StrokeWeight {
    /// One weight is used for all sides.
    Uniform(f32),
    /// Individual weights for each side (typically only applied on boxes).
    Individual { top: f32, right: f32, bottom: f32, left: f32 },
}

/// A stroke is similar to a border, except that it does not change layout (a border insets
/// the children by the border size), it may be inset, centered or outset from the view bounds
/// and there can be multiple strokes on a view.
#[derive(Clone, Debug, PartialEq, Deserialize, Serialize)]
pub struct Stroke {
    /// The alignment of strokes on this view.
    pub stroke_align: StrokeAlign,
    /// The thickness of strokes on this view (in pixels).
    pub stroke_weight: StrokeWeight,
    /// The stroke colors/fills
    pub strokes: Vec<Background>,
}

impl Default for Stroke {
    fn default() -> Self {
        Stroke {
            stroke_align: StrokeAlign::Center,
            stroke_weight: StrokeWeight::Uniform(0.0),
            strokes: Vec::new(),
        }
    }
}
