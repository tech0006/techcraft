package com.tech0006.techcraft.util.tools.inventory;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.util.List;
import java.util.regex.Pattern;

public class Tooltip {
    private static final Pattern COMPILE = Pattern.compile("@", Pattern.LITERAL);

    public static void showInfoCtrlCoalGenerator(int energy, List<Component> tooltip) {
        if (Screen.hasControlDown()) {
            String s = "Energy: " + energy;
            addInformationLocalized(tooltip, "message.techcraft.ctrl_info", s);
        } else
            addInformationLocalized(tooltip, "message.techcraft.hold_ctrl", "");
    }

    public static void showInfoCtrlElectricFurnace(int energy, List<Component> tooltip) {
        if (Screen.hasControlDown()) {
            String s = "Energy: " + energy;
            addInformationLocalized(tooltip, "message.techcraft.ctrl_info", s);
        } else
            addInformationLocalized(tooltip, "message.techcraft.hold_ctrl", "");
    }

    public static void showInfoCtrlElectricCrusher(int energy, List<Component> tooltip) {
        if (Screen.hasControlDown()) {
            String s = "Energy: " + energy;
            addInformationLocalized(tooltip, "message.techcraft.ctrl_info", s);
        } else
            addInformationLocalized(tooltip, "message.techcraft.hold_ctrl", "");
    }

    private static void addInformationLocalized(List<Component> tooltip, String key, String parameters) {
        String translated = I18n.get(key) + parameters;
        translated = COMPILE.matcher(translated).replaceAll("\u00a7");
        String[] formatted = translated.split("\n");
        for (String line : formatted)
            tooltip.add(new TextComponent(line));
    }
}
