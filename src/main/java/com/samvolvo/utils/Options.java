package com.samvolvo.utils;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Options {

    public OptionData orderId(){
        OptionData option = new OptionData(OptionType.STRING, "orderid", "What is the orderId?", false);
        return option;
    }

    public OptionData customer(){
        OptionData option = new OptionData(OptionType.USER, "customer", "Who is the customer?", false);
        return option;
    }

    public OptionData totalPrice(){
        OptionData option = new OptionData(OptionType.NUMBER, "totalprice", "What is the total?", false);
        return option;
    }

    public OptionData paymentMethod(){
        OptionData option = new OptionData(OptionType.STRING, "paymentmethod", "What is the paymentMethod?", false);
        return option;
    }

    public OptionData orderStatus(){
        OptionData option = new OptionData(OptionType.STRING, "orderstatus", "What is the status?", false);
        option.addChoice("progress", "progress");
        option.addChoice("delivered", "delivered");
        option.addChoice("cancelled", "cancelled");
        return option;
    }

    public OptionData supporter(){
        OptionData option = new OptionData(OptionType.USER, "supporter", "Who is the supporter?", false);
        return option;
    }

    public OptionData notes(){
        OptionData option = new OptionData(OptionType.STRING, "notes", "Any notes?", false);
        return option;
    }

    public OptionData orderIdForced(){
        OptionData option = new OptionData(OptionType.STRING, "orderid", "What is the orderId?", true);
        return option;
    }

    public OptionData customerForced(){
        OptionData option = new OptionData(OptionType.USER, "customer", "Who is the customer?", true);
        return option;
    }

    public OptionData totalPriceForced(){
        OptionData option = new OptionData(OptionType.NUMBER, "totalprice", "What is the total?", true);
        return option;
    }

    public OptionData paymentMethodForced(){
        OptionData option = new OptionData(OptionType.STRING, "paymentmethod", "What is the paymentMethod?", true);
        return option;
    }

    public OptionData orderStatusForced(){
        OptionData option = new OptionData(OptionType.STRING, "orderstatus", "What is the status?", true);
        option.addChoice("Null", "null");
        option.addChoice("delivered", "delivered");
        option.addChoice("cancelled", "cancelled");
        return option;
    }

    public OptionData supporterForced(){
        OptionData option = new OptionData(OptionType.USER, "supporter", "Who is the supporter?", true);
        return option;
    }

    public OptionData notesForced(){
        OptionData option = new OptionData(OptionType.STRING, "notes", "Any notes?", true);
        return option;
    }

}
