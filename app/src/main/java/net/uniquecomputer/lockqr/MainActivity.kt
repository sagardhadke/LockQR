package net.uniquecomputer.lockqr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import net.uniquecomputer.lockqr.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val parentIteam = ArrayList<ParentItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainRecyclerview.setHasFixedSize(true)
        binding.mainRecyclerview.layoutManager = LinearLayoutManager(this)

        addDataList()
        val adapter = ParentAdapter(this, parentList = parentIteam)
        binding.mainRecyclerview.adapter = adapter
    }

    private fun addDataList() {
        val childIteam1 = ArrayList<ChildItem>()
        childIteam1.add(ChildItem("Text", R.drawable.text))
        childIteam1.add(ChildItem("Website", R.drawable.website))
        childIteam1.add(ChildItem("Email", R.drawable.email))
        childIteam1.add(ChildItem("SMS", R.drawable.message))
        childIteam1.add(ChildItem("Wi-Fi", R.drawable.wifi))
        childIteam1.add(ChildItem("Phone", R.drawable.call))
        childIteam1.add(ChildItem("Contact", R.drawable.contact))
        childIteam1.add(ChildItem("Calender", R.drawable.calendar))

        parentIteam.add(ParentItem("General", childIteam1))

        val childIteam2 = ArrayList<ChildItem>()
        childIteam2.add(ChildItem("Youtube", R.drawable.youtube))
        childIteam2.add(ChildItem("Whatsapp", R.drawable.whatsapp))
        childIteam2.add(ChildItem("Facebook", R.drawable.facebook))
        childIteam2.add(ChildItem("Twitter", R.drawable.twitter))
        childIteam2.add(ChildItem("Linkedin", R.drawable.linkedin))
        childIteam2.add(ChildItem("Instagram", R.drawable.instagram))
        childIteam2.add(ChildItem("Telegram", R.drawable.telegram))
        childIteam2.add(ChildItem("Messenger", R.drawable.messenger))

        parentIteam.add(ParentItem("Social", childIteam2))

        val child3 = ArrayList<ChildItem>()
        child3.add(ChildItem("Spotify", R.drawable.spotify))
        child3.add(ChildItem("Snapchat", R.drawable.snapchat))
        child3.add(ChildItem("Skype", R.drawable.skype))
        child3.add(ChildItem("Paypal", R.drawable.paypal))
        child3.add(ChildItem("Pinterest", R.drawable.pinterest))

        parentIteam.add(ParentItem("Other", child3))
    }
}