package com.atandroidlabs.beatcovid_fightagainstthecovid_19

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception


class VaccinationFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var vaccineHeadingTextView: TextView
    private lateinit var vaccineSubTextView: TextView
    private lateinit var howToRegisterHeadingTextView: TextView
    private lateinit var howToRegisterContentTextView: TextView
    private lateinit var registerButtonMain: MaterialButton
    private lateinit var registerButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun goToCoWinPortal() {
        val uri: Uri = Uri.parse("https://www.cowin.gov.in/home") // missing 'http://' will cause crashed
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun fetchFAQQuestions() {
        if (MainActivity.language.contentEquals(getString(R.string.language_english_key))) {
            fetchEnglishFAQ()
        } else {
            fetchHindiFAQ()
        }
    }

    private fun fetchHindiFAQ() {
        val questions: ArrayList<FAQ_Question> = ArrayList<FAQ_Question>()
        questions.add(
            FAQ_Question(
                false,
                getString(R.string.question1_hindi),
                getString(R.string.answer1_hindi)
            )
        )
        questions.add(
            FAQ_Question(
                false,
                getString(R.string.question2_hindi),
                getString(R.string.answer2_hindi)
            )
        )
        questions.add(
            FAQ_Question(
                false,
                getString(R.string.question3_hindi),
                getString(R.string.answer3_hindi)
            )
        )
        questions.add(
            FAQ_Question(
                false,
                getString(R.string.question4_hindi),
                getString(R.string.answer4_hindi)
            )
        )
        questions.add(
            FAQ_Question(
                false,
                getString(R.string.question5_hindi),
                getString(R.string.answer5_hindi)
            )
        )

        val db = FirebaseFirestore.getInstance()
        db.collection("FAQ").whereEqualTo("isHindi", true)
            .get().addOnCompleteListener { task->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val ques = document.get("question") as String
                        val ans = document.get("answer") as String
                        questions.add(FAQ_Question(false, ques, ans))
                    }

                } else {
                    //Task failed
                }

                recyclerView.adapter = FAQAdapter(requireContext(), questions)
            }
    }

    private fun fetchEnglishFAQ() {
        val questions: ArrayList<FAQ_Question> = ArrayList<FAQ_Question>()
        questions.add(
            FAQ_Question(
                false,
                getString(R.string.question1),
                getString(R.string.answer1)
            )
        )
        questions.add(
            FAQ_Question(
                false,
                getString(R.string.question2),
                getString(R.string.answer2)
            )
        )
        questions.add(
            FAQ_Question(
                false,
                getString(R.string.question3),
                getString(R.string.answer3)
            )
        )
        questions.add(
            FAQ_Question(
                false,
                getString(R.string.question4),
                getString(R.string.answer4)
            )
        )
        questions.add(
            FAQ_Question(
                false,
                getString(R.string.question5),
                getString(R.string.answer5)
            )
        )

        val db = FirebaseFirestore.getInstance()
        db.collection("FAQ").whereEqualTo("isHindi", false)
            .get().addOnCompleteListener { task->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val ques = document.get("question") as String
                        val ans = document.get("answer") as String
                        questions.add(FAQ_Question(false, ques, ans))
                    }
                } else {
                    //Task failed
                }
                try {
                    if (context != null)
                        recyclerView.adapter = FAQAdapter(requireContext(), questions)
                } catch (e: Exception) {

                }

            }
    }

    private fun init() {
        val language = MainActivity.language
        if (language.contentEquals(getString(R.string.language_english_key))) {
            vaccineHeadingTextView.text = getString(R.string.vaccines_are_safe_english)
            vaccineSubTextView.text = getString(R.string.vaccine_safe_text_long_english)
            howToRegisterHeadingTextView.text = getString(R.string.how_to_register_on_cowin_english)
            howToRegisterContentTextView.text = getString(R.string.register_cowin_english)
        } else {
            vaccineHeadingTextView.text = getString(R.string.vaccines_are_safe_hindi)
            vaccineSubTextView.text = getString(R.string.vaccine_safe_text_long_hindi)
            howToRegisterHeadingTextView.text = getString(R.string.how_to_register_on_cowin_hindi)
            howToRegisterContentTextView.text = getString(R.string.register_cowin_hindi)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vaccine, container, false)

        vaccineHeadingTextView = view.findViewById(R.id.vaccine_title_text)
        vaccineSubTextView = view.findViewById(R.id.vaccine_sub_text)
        howToRegisterHeadingTextView = view.findViewById(R.id.how_to_register_title)
        howToRegisterContentTextView = view.findViewById(R.id.how_to_register_content)

        registerButtonMain = view.findViewById(R.id.register_button_main)
        registerButton = view.findViewById(R.id.register_button)

        registerButtonMain.setOnClickListener {
            goToCoWinPortal()
        }

        registerButton.setOnClickListener {
            goToCoWinPortal()
        }

        recyclerView = view.findViewById(R.id.faq_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        fetchFAQQuestions()
        init()
        return view
    }
}