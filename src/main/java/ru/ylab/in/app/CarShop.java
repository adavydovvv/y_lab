package ru.ylab.in.app;


import ru.ylab.in.menu.MainDisplay;
import ru.ylab.out.config.LiquiBase;

import java.sql.SQLException;

public class CarShop {
    public static void main(String[] args) throws InterruptedException, SQLException {
        LiquiBase.migration();
        MainDisplay.display();
    }

}

