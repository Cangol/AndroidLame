/*
 *
 *  Copyright (c) 2013 Cangol
 *   <p/>
 *   Licensed under the Apache License, Version 2.0 (the "License")
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *  <p/>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p/>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package mobi.cangol.mobile.utils;

/**
 * Created by xuewu.wei on 2016/6/13.
 */
public class LameUtils {
    private int bitRate = 96;
    private int sampleRate = 16000;
    private int numChannels = 1;

    static {
        System.loadLibrary("mp3lame");
    }
    public native String getSystemABI();

    public native String getVersion();

    private native void initEncoder(int numChannels, int sampleRate, int bitRate, int mode, int quality);

    private native void destroyEncoder();

    private native int encodeFile(String sourcePath, String targetPath);

    public int getNumChannels() {
        return this.numChannels;
    }

    public void setNumChannels(int numChannels) {
        this.numChannels = numChannels;
    }

    public int getSampleRate() {
        return this.sampleRate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public int getBitRate() {
        return this.bitRate;
    }

    public void setBitRate(int bitRate) {
        this.bitRate = bitRate;
    }

    public LameUtils() {
    }

    public LameUtils(int numChannels, int sampleRate, int bitRate) {
        this.numChannels = numChannels;
        this.sampleRate = sampleRate;
        this.bitRate = bitRate;
    }

    public boolean raw2mp3(String source, String destination) {
        initEncoder(this.numChannels, this.sampleRate, this.bitRate, 1, 2);
        int result = encodeFile(source, destination);
        destroyEncoder();
        if (result == 0) {
            return true;
        }
        return false;
    }
}
