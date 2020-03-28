package com.imquarantined.util.helper

object AnimationUtil {

  /*  fun bubbleAnimation(view: View, amplitude: Double = 0.3, frequency: Double = 30.0) {
        val bounceAnim =
            loadAnimation(
                R.anim.bounce
            )
        bounceAnim.interpolator =
            BounceInterpolator(
                amplitude,
                frequency
            )
        view.startAnimation(bounceAnim)
    }

    fun flipInAnimation(context: Context, view: View, distance: Int = 8000) {
        val scale = context.resources.displayMetrics.density
        view.cameraDistance = distance * scale

        val flipIn =
            loadAnimatorSet(
                R.animator.flip_in
            )
        flipIn.setTarget(view)
        addAnimatorSetListener(
            view,
            flipIn,
            visibleOnStart = true
        )
        flipIn.start()
    }

    fun flipOutAnimation(context: Context, view: View, distance: Int = 8000) {
        val scale = context.resources.displayMetrics.density
        view.cameraDistance = distance * scale

        val flipOut =
            loadAnimatorSet(
                R.animator.flip_out
            )
        flipOut.setTarget(view)
        addAnimatorSetListener(
            view,
            flipOut,
            inVisibleAfterEnd = true
        )
        flipOut.start()
    }

    private fun loadAnimation(@AnimRes anim: Int): Animation {
        return AnimationUtils.loadAnimation(
            StylineAndroidApp.getApplicationContext(), anim
        )
    }

    private fun loadAnimatorSet(@AnimatorRes animSet: Int): AnimatorSet {
        return AnimatorInflater.loadAnimator(
            StylineAndroidApp.getApplicationContext(), animSet
        ) as AnimatorSet
    }

    private fun addAnimatorSetListener(
        view: View,
        animatorSet: AnimatorSet,
        visibleOnStart: Boolean = false,
        inVisibleAfterEnd: Boolean = false
    ) {
        animatorSet.addListener(object: Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                if(visibleOnStart) view.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animator?) {
                if(inVisibleAfterEnd) view.visibility = View.INVISIBLE
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator?) {

            }
        })
    }*/
}