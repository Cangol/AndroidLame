#include <stdio.h>
#include <stdlib.h>
#include <jni.h>
#include <android/log.h>
#include "lame.h"

#define LOG_TAG "LAME ENCODER"
#define LOGD(format, args...)  __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, format, ##args);
#define BUFFER_SIZE 8192
#define be_short(s) ((short) ((unsigned short) (s) << 8) | ((unsigned short) (s) >> 8))

lame_t lame;

int read_samples(FILE *input_file, short *input) {
	int nb_read;
	nb_read = fread(input, 1, sizeof(short), input_file) / sizeof(short);

	int i = 0;
	while (i < nb_read) {
		input[i] = be_short(input[i]);
		i++;
	}

	return nb_read;
}

JNIEXPORT void JNICALL
Java_mobi_cangol_mobile_utils_LameUtils_initEncoder(JNIEnv *env, jobject jobj, jint numChannels,
													jint sampleRate, jint bitRate, jint mode,
													jint quality) {
	lame = lame_init();

	LOGD("Init parameters:");
	lame_set_num_channels(lame, numChannels);
	LOGD("Number of channels: %d", numChannels);
	lame_set_in_samplerate(lame, sampleRate);
	LOGD("Sample rate: %d", sampleRate);
	lame_set_brate(lame, bitRate);
	LOGD("Bitrate: %d", bitRate);
	lame_set_mode(lame, mode);
	LOGD("Mode: %d", mode);
	lame_set_quality(lame, quality);
	LOGD("Quality: %d", quality);

	int res = lame_init_params(lame);
	LOGD("Init returned: %d", res);

}
JNIEXPORT void JNICALL
Java_mobi_cangol_mobile_utils_LameUtils_destroyEncoder(JNIEnv *env, jobject instance) {

	int res = lame_close(lame);
	LOGD("Deinit returned: %d", res);

}
JNIEXPORT jint JNICALL
Java_mobi_cangol_mobile_utils_LameUtils_encodeFile(JNIEnv *env, jobject instance,
												   jstring sourcePath_, jstring targetPath_) {
	const char *sourcePath = (*env)->GetStringUTFChars(env, sourcePath_, 0);
	const char *targetPath = (*env)->GetStringUTFChars(env, targetPath_, 0);

	// TODO

	(*env)->ReleaseStringUTFChars(env, sourcePath_, sourcePath);
	(*env)->ReleaseStringUTFChars(env, targetPath_, targetPath);

	FILE *input_file, *output_file;
	input_file = fopen(sourcePath, "rb");
	output_file = fopen(targetPath, "wb");

	short input[BUFFER_SIZE];
	char output[BUFFER_SIZE];
	int nb_read = 0;
	int nb_write = 0;
	int nb_total = 0;

	LOGD("Encoding started");
	while (nb_read = read_samples(input_file, input)) {
		nb_write = lame_encode_buffer(lame, input, input, nb_read, output,
									  BUFFER_SIZE);
		fwrite(output, nb_write, 1, output_file);
		nb_total += nb_write;
	}
	LOGD("Encoded %d bytes", nb_total);

	nb_write = lame_encode_flush(lame, output, BUFFER_SIZE);
	fwrite(output, nb_write, 1, output_file);
	LOGD("Flushed %d bytes", nb_write);

	fclose(input_file);
	fclose(output_file);
}

JNIEXPORT jstring JNICALL
Java_mobi_cangol_mobile_utils_LameUtils_getVersion(JNIEnv *env, jobject instance) {

	return (*env)->NewStringUTF(env, get_lame_version());
}