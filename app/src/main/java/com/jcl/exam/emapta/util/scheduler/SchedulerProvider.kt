package com.jcl.exam.emapta.util.scheduler

import io.reactivex.Scheduler

/**
 * Created by jaylumba on 05/16/2018.
 */

interface SchedulerProvider {
    fun ui(): Scheduler
    fun computation(): Scheduler
    fun trampoline(): Scheduler
    fun newThread(): Scheduler
    fun io(): Scheduler
}
