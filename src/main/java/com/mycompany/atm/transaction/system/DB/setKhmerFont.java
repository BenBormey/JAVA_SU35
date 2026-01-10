package com.mycompany.atm.transaction.system.DB;

import java.awt.*;

public class setKhmerFont {
    public  void setFont(Component c) {
        try {
            // កំណត់ប្រើ Khmer OS Battambang
            Font khmerOS = new Font("Khmer OS Battambang", c.getFont().getStyle(), c.getFont().getSize());
            c.setFont(khmerOS);
        } catch (Exception ignored) {
            // បើគ្មាន Khmer OS ទេ ឱ្យវាប្រើ SansSerif (ស្គាល់ខ្មែរដូចគ្នា)
            c.setFont(new Font("SansSerif", c.getFont().getStyle(), c.getFont().getSize()));
        }

        if (c instanceof Container) {
            for (Component child : ((Container) c).getComponents()) {
                setFont(child);
            }
        }
    }
}
