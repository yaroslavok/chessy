package io.chessy.tool

import org.bytedeco.ffmpeg.global.avcodec
import org.bytedeco.ffmpeg.global.avutil
import org.bytedeco.javacv.FFmpegFrameRecorder
import org.bytedeco.javacv.Java2DFrameConverter
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.InputStream

interface VideoMaker {
    fun startRecord()

    fun addFrame(bufferedImage: BufferedImage)

    fun endRecord(): InputStream
}

class FFmpegVideoMaker(width: Int, height: Int, fps: Int, outputFilePath: String) : VideoMaker {
    private val recorder = FFmpegFrameRecorder(outputFilePath, width, height)
    private val converter = Java2DFrameConverter()

    init {
        recorder.videoCodec = avcodec.AV_CODEC_ID_H264
        recorder.pixelFormat = avutil.AV_PIX_FMT_YUV420P
        recorder.frameRate = fps.toDouble()
        recorder.format = "mp4"
    }

    override fun startRecord() {
        try {
            recorder.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun addFrame(bufferedImage: BufferedImage) {
        try {
            recorder.record(converter.getFrame(bufferedImage))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun endRecord(): InputStream {
        recorder.stop()
        recorder.release()
        return ByteArrayInputStream(ByteArray(0))
    }
}