package com.example.quizzapp.Activity

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.quizzapp.Adapter.QuestionAdapter
import com.example.quizzapp.Domain.QuestionModel
import com.example.quizzapp.R
import com.example.quizzapp.databinding.ActivityQuestionBinding

class QuestionActivity : AppCompatActivity(), QuestionAdapter.score {
    private lateinit var binding: ActivityQuestionBinding
    private var position: Int = 0
    private lateinit var receivedList: MutableList<QuestionModel>
    private var allScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window: Window = this@QuestionActivity.window
        window.statusBarColor = ContextCompat.getColor(this@QuestionActivity, R.color.grey)

        receivedList = intent.getParcelableArrayListExtra<QuestionModel>("list")!!.toMutableList()

        binding.apply {
            backBtn.setOnClickListener { finish() }
            progressBar.progress = 1
            questionTxt.text = receivedList[position].question
            val drawableResourceId: Int = binding.root.resources.getIdentifier(
                receivedList[position].picPath, "drawable", binding.root.context.packageName
            )
            Glide.with(this@QuestionActivity)
                .load(drawableResourceId)
                .centerCrop()
                .apply(RequestOptions.bitmapTransform(RoundedCorners(60)))
                .into(pic)

            loadAnswer()

            rightArrow.setOnClickListener {
                if (progressBar.progress == 10) {
                    val intent = Intent(this@QuestionActivity, ScoreActivity::class.java)
                    intent.putExtra("Score", allScore)
                    startActivity(intent)
                    finish()
                    return@setOnClickListener
                }
                position++
                progressBar.progress += 1
                questionNumberTxt.text = "Question ${progressBar.progress}/10"
                questionTxt.text = receivedList[position].question

                val drawableResouId: Int = binding.root.resources.getIdentifier(
                    receivedList[position].picPath, "drawable", binding.root.context.packageName
                )
                Glide.with(this@QuestionActivity)
                    .load(drawableResouId)
                    .centerCrop()
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(60)))
                    .into(pic)
                loadAnswer()
            }

            leftArrow.setOnClickListener {
                if (progressBar.progress == 1) {
                    return@setOnClickListener
                }
                position--
                progressBar.progress -= 1
                questionNumberTxt.text = "Question ${progressBar.progress}/10"
                questionTxt.text = receivedList[position].question

                val drawableResouId: Int = binding.root.resources.getIdentifier(
                    receivedList[position].picPath, "drawable", binding.root.context.packageName
                )
                Glide.with(this@QuestionActivity)
                    .load(drawableResouId)
                    .centerCrop()
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(60)))
                    .into(pic)
                loadAnswer()
            }
        }
    }

    private fun loadAnswer() {
        val users: MutableList<String> = mutableListOf(
            receivedList[position].answer1.toString(),
            receivedList[position].answer2.toString(),
            receivedList[position].answer3.toString(),
            receivedList[position].answer4.toString()
        )
        receivedList[position].clickedAnswer?.let {
            users.add(it.toString())
        }

        val questionAdapter = QuestionAdapter(
            receivedList[position].correctAnswer.toString(), users, this
        )
        questionAdapter.differ.submitList(users)
        binding.questionList.apply {
            layoutManager = LinearLayoutManager(this@QuestionActivity)
            adapter = questionAdapter
        }
    }

    override fun amount(number: Int, clickedAnswer: String) {
        allScore += number
        receivedList[position].clickedAnswer = clickedAnswer
    }
}
