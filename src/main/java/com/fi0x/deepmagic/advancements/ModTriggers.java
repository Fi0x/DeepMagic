package com.fi0x.deepmagic.advancements;

public class ModTriggers
{
    public static final CustomTrigger RIGHT_CLICK_SPELL = new CustomTrigger("right_click_spell");
    public static final CustomTrigger LINK_MANA_RELAY = new CustomTrigger("link_mana_relay");
    public static final CustomTrigger KNOWLEDGE_UI_OPENED = new CustomTrigger("altar_of_knowledge_ui_opened");

    public static final CustomTrigger[] TRIGGER_ARRAY = new CustomTrigger[]{
            RIGHT_CLICK_SPELL,
            LINK_MANA_RELAY,
            KNOWLEDGE_UI_OPENED
    };
}