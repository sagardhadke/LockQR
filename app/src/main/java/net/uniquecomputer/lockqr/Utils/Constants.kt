package net.uniquecomputer.lockqr.Utils

import net.uniquecomputer.lockqr.ChildItem
import net.uniquecomputer.lockqr.ParentItem
import net.uniquecomputer.lockqr.R

class Constants {

    companion object{
         var parentItem = ArrayList<ParentItem>()
        fun addDataList() {

            val childItem1 = ArrayList<ChildItem>()
            childItem1.add(ChildItem("Text", R.drawable.text))
            childItem1.add(ChildItem("Website", R.drawable.website))
            childItem1.add(ChildItem("Email", R.drawable.email))
            childItem1.add(ChildItem("SMS", R.drawable.message))
            childItem1.add(ChildItem("Wi-Fi", R.drawable.wifi))
            childItem1.add(ChildItem("Phone", R.drawable.call))
            childItem1.add(ChildItem("Contact", R.drawable.contact))
            childItem1.add(ChildItem("Calender", R.drawable.calendar))

            parentItem.add(ParentItem("General", childItem1))

            val childItem2 = ArrayList<ChildItem>()
            childItem2.add(ChildItem("Youtube", R.drawable.youtube))
            childItem2.add(ChildItem("Whatsapp", R.drawable.whatsapp))
            childItem2.add(ChildItem("Facebook", R.drawable.facebook))
            childItem2.add(ChildItem("Twitter", R.drawable.twitter))
            childItem2.add(ChildItem("Linkedin", R.drawable.linkedin))
            childItem2.add(ChildItem("Instagram", R.drawable.instagram))
            childItem2.add(ChildItem("Telegram", R.drawable.telegram))
            childItem2.add(ChildItem("Messenger", R.drawable.messenger))

            parentItem.add(ParentItem("Social", childItem2))

            val child3 = ArrayList<ChildItem>()
            child3.add(ChildItem("Spotify", R.drawable.spotify))
            child3.add(ChildItem("Snapchat", R.drawable.snapchat))
            child3.add(ChildItem("Skype", R.drawable.skype))
            child3.add(ChildItem("Paypal", R.drawable.paypal))
            child3.add(ChildItem("Pinterest", R.drawable.pinterest))

            parentItem.add(ParentItem("Other", child3))
        }

    }

}