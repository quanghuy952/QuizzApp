package com.example.quizzapp.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.quizzapp.Adapter.LeaderAdapter
import com.example.quizzapp.Domain.UserModel
import com.example.quizzapp.R
import com.example.quizzapp.databinding.ActivityLeaderBinding

class LeaderActivity : AppCompatActivity() {
    lateinit var binding : ActivityLeaderBinding
    private val leaderAdapter by lazy { LeaderAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLeaderBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val window: Window =this@LeaderActivity.window
        window.statusBarColor= ContextCompat.getColor(this@LeaderActivity, R.color.grey)

        binding.apply {
            scoreTop1Txt.text = loadData().get(0).score.toString()
            scoreTop2Txt.text = loadData().get(1).score.toString()
            scoreTop3Txt.text = loadData().get(2).score.toString()
            titleTop1Txt.text = loadData().get(0).name
            titleTop2Txt.text = loadData().get(1).name
            titleTop3Txt.text = loadData().get(2).name

            val drawableResourceId1 :Int = binding.root.resources.getIdentifier(
                loadData().get(0).pic,"drawable",binding.root.context.packageName
            )
            Glide.with(root.context).load(drawableResourceId1).into(pic1)

            val drawableResourceId2 :Int = binding.root.resources.getIdentifier(
                loadData().get(1).pic,"drawable",binding.root.context.packageName
            )
            Glide.with(root.context).load(drawableResourceId2).into(pic2)

            val drawableResourceId3 :Int = binding.root.resources.getIdentifier(
                loadData().get(2).pic,"drawable",binding.root.context.packageName
            )
            Glide.with(root.context).load(drawableResourceId3).into(pic3)


            bottomMenu.setItemSelected(R.id.Board)
            bottomMenu.setOnItemSelectedListener{
                if(it==R.id.home){
                    startActivity(Intent(this@LeaderActivity,MainActivity::class.java))
                }
            }
            var list:MutableList<UserModel> = loadData()
            list.removeAt(0)
            list.removeAt(1)
            list.removeAt(2)
            leaderAdapter.differ.submitList(list)

            leaderView.apply {
                layoutManager = LinearLayoutManager(this@LeaderActivity)
                adapter = leaderAdapter
            }
        }
    }


    private fun loadData():MutableList<UserModel>{
        val users:MutableList<UserModel> = mutableListOf()
        users.add(UserModel(1,"Sophia","person1",5))
        users.add(UserModel(2,"Daniel","person2",2))
        users.add(UserModel(3,"James","person3",3))
        users.add(UserModel(4,"John","person4",2))
        users.add(UserModel(5,"Emily","person5",5))
        users.add(UserModel(6,"David","person6",1))
        users.add(UserModel(7,"Sarah","person7",2))
        users.add(UserModel(8,"Michael","person8",5))
        users.add(UserModel(9,"Bob","person9",5))
        users.add(UserModel(10,"Wilson","person10",7))

        return users
    }


}