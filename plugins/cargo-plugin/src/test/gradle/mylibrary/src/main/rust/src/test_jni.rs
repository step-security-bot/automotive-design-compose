// Copyright 2023 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

extern crate jni;

use jni::objects::{JClass, JString};
use jni::sys::jstring;
use jni::JNIEnv;
use ring::rand;
use ring::rand::SecureRandom;


// A copy of a test from the ring crate. The ring crate is a bit trickier to cross compile
// (https://github.com/briansmith/ring/blob/main/BUILDING.md) so it provides a good test.
fn test_system_random_lengths() {
    const LINUX_LIMIT: usize = 256;
    const WEB_LIMIT: usize = 65536;

    // Test that `fill` succeeds for various interesting lengths. `256` and
    // multiples thereof are interesting because that's an edge case for
    // `getrandom` on Linux.
    let lengths = [
        0,
        1,
        2,
        3,
        96,
        LINUX_LIMIT - 1,
        LINUX_LIMIT,
        LINUX_LIMIT + 1,
        LINUX_LIMIT * 2,
        511,
        512,
        513,
        4096,
        WEB_LIMIT - 1,
        WEB_LIMIT,
        WEB_LIMIT + 1,
        WEB_LIMIT * 2,
    ];

    for len in lengths.iter() {
        let mut buf = vec![0; *len];

        let rng = rand::SystemRandom::new();
        assert!(rng.fill(&mut buf).is_ok());

        // If `len` < 96 then there's a big chance of false positives, but
        // otherwise the likelihood of a false positive is so too low to
        // worry about.
        if *len >= 96 {
            assert!(buf.iter().any(|x| *x != 0));
        }
    }
}


#[no_mangle]
pub extern "system" fn Java_com_android_designcompose_testproj_mylibrary_HelloJni_hello(
    env: JNIEnv,
    _class: JClass,
    input: JString,
) -> jstring {
    let input: String = env.get_string(input).expect("Couldn't get java string!").into();

    test_system_random_lengths();

    let output =
        env.new_string(format!("Hello, {}!", input)).expect("Couldn't create java string!");

    output.into_raw()
}
