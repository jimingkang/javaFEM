/*
package org.sustech.fem;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class TEST extends Thread {

private static native void petsc_init();

private native int petsc_multiply(int thread);

private static native void petsc_finalize();

private CyclicBarrier barrier;

public TEST(CyclicBarrier barrier, String name) {

super(name);

this.barrier = barrier;

}

@Override

public void run() {

try {

barrier.await();

petsc_multiply(0);

}catch(InterruptedException  e){
    e.printStackTrace();
} catch ( BrokenBarrierException e) {

e.printStackTrace();

}

}

public static void main(String args[]) throws InterruptedException, BrokenBarrierException {

System.loadLibrary("test");

CyclicBarrier barrier = new CyclicBarrier(2);

TEST first = new TEST(barrier, "1");

TEST second = new TEST(barrier, "2");

petsc_init();

first.start();

second.start();

}

}
 JNIEXPORT void JNICALL Java_TEST_petsc_1init

(JNIEnv *env, jclass class){

printf("Petsc init\n");

PetscInitialize(NULL, NULL, 0, help);

}

JNIEXPORT void JNICALL Java_TEST_petsc_1finalize

(JNIEnv *env, jclass class){

printf("Petsc finalize\n");

PetscFinalize();

}

JNIEXPORT int JNICALL Java_TEST_petsc_1multiply

(JNIEnv *env, jobject obj, jint thread)

{

printf("Hello from thread %d\n", thread);

Mat A;

Vec B;

PetscErrorCode err;

MPI_Comm

comm;

comm = PETSC_COMM_SELF;

err = MatCreate(comm, &A); // HERE is where it fails

CHKERRQ(err);

return 0;

}



#include <jni.h>

*/
/* Header for class TEST *//*


#ifndef _Included_TEST

#define _Included_TEST

#ifdef __cplusplus

extern "C" {

#endif

#undef TEST_MIN_PRIORITY

#define TEST_MIN_PRIORITY 1L

#undef TEST_NORM_PRIORITY

#define TEST_NORM_PRIORITY 5L

#undef TEST_MAX_PRIORITY

#define TEST_MAX_PRIORITY 10L

*/
/*

* Class:

TEST

* Method:

petsc_init

* Signature: ()V

*//*


JNIEXPORT void JNICALL Java_TEST_petsc_1init

(JNIEnv *, jclass);

*/
/*

* Class:

TEST

* Method:

petsc_multiply

* Signature: (I)I

*//*


JNIEXPORT jint JNICALL Java_TEST_petsc_1multiply

(JNIEnv *, jobject, jint);

*/
/*

* Class:

TEST

* Method:

petsc_finalize

* Signature: ()V

*//*


JNIEXPORT void JNICALL Java_TEST_petsc_1finalize

(JNIEnv *, jclass);

#ifdef __cplusplus

}

#endif

#endif*/
