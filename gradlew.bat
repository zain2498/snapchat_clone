<?xml version="1.0" encoding="utf-8"?>
<!-- Copyright (C) 2010 The Android Open Source Project

     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at

          http://www.apache.org/licenses/LICENSE-2.0

     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.
-->

<selector xmlns:android="http://schemas.android.com/apk/res/android">

    <!-- Even though these two point to the same resource, have two states so the drawable will invalidate itself when coming out of pressed state. -->
    <item android:state_focused="true"  android:state_enabled="false" android:state_pressed="true" android:drawable="@drawable/abc_list_selector_disabled_holo_light" />
    <item android:state_focused="true"  android:state_enabled="false"                              android:drawable="@drawable/abc_list_selector_disabled_holo_light" />
    <item android:state_focused="true"                                android:state_pressed="true" android:drawable="@drawable/abc_list_selector_background_transition_holo_light" />
    <item android:state_focused="false"                               android:state_pressed="true" android:drawable="@drawable/abc_list_selector_background_transition_holo_light" />
    <item android:state_focused="true"                                                             android:drawable="@drawable/abc_list_focused_holo" />
    <item                                                                                          android:drawable="@android:color/transparent" />
</selector>
                                                                                                                                                                                                                                                                                                                                                               