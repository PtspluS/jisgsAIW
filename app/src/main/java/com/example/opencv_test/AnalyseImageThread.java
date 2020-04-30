package com.example.opencv_test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.opencv.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgcodecs.Imgcodecs;

/*
* As a thread, it will analyse only one picture per time.
* */

public class AnalyseImageThread extends Thread {

    private String pathSrc;
    private boolean globalImage;
    private Mat srcImage;
    // for only one picture
    private Mat dstImage;
    // for list of picture
    private ArrayList<Mat> piecesImage;
    private int id;
    private ArrayList<String> pathNewImages;

    public AnalyseImageThread() {

    }

    /**
     * Function run when a picture is took
     * @param pathImage : path to the jpeg image
     * @param globalImg : if true means that it's the picture of the global jigsaw
     * @param idx : id of the project
     * @return tab of all the new path for the new analysed images
     */
    public ArrayList<String> run(String pathImage, boolean globalImg, int idx){
        globalImage = globalImg;
        pathSrc = pathImage;
        // create a matrix of the image
        Bitmap bit = BitmapFactory.decodeFile(pathImage);
        //srcImage = Imgcodecs.imread(pathImage);
        //srcImage = Highgui.imread(file.getAbsolutePath());
        Utils.bitmapToMat(bit, srcImage);
        id = idx;

        if(globalImage){
            dstImage = correctPerspective(srcImage);
        } else{
            piecesImage = cutPieces(srcImage);
        }
        pathNewImages = save();
        return pathNewImages;
    }

    private Mat correctPerspective(Mat img){
        // copy img to src
        Mat src = img.clone();
        // we put the image in gray, it's better for analyse
        Mat gray = new Mat(src.rows(), src.cols(), src.type());
        Mat thresh = new Mat(src.rows(), src.cols(), src.type());

        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        // we add a blur on the image to analyse it better again
        Size ksize = new Size(5,5);
        Imgproc.GaussianBlur(gray, gray,ksize,0 );
        Imgproc.threshold(gray, thresh, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV);
        // now thresh is the matrix were the line are in 255. From here thresh is the src image
        // now trying to determined the contour of the image
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(thresh, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        double area;
        double maxArea =0;
        MatOfPoint maxContours = new MatOfPoint();
        double epsi;
        double peri;
        // try to dertermine the max area
        for(MatOfPoint c : contours){
            area = Imgproc.contourArea(c);
            if(area>maxArea){
                maxArea = area;
                maxContours = c;
            }
        }
        peri = Imgproc.arcLength(new MatOfPoint2f(maxContours.toArray()),true);
        MatOfPoint2f poly = new MatOfPoint2f();
        Imgproc.approxPolyDP(new MatOfPoint2f(maxContours.toArray()), poly, 0.02*peri, true);


        // delete this line after but by default you return the original image
        return img;
    }


    private ArrayList<Mat> cutPieces(Mat src){

        Mat img = src.clone();

        // we put the image in gray, it's better for analyse
        Mat gray = new Mat(src.rows(), src.cols(), src.type());
        Mat thresh = new Mat(src.rows(), src.cols(), src.type());

        Imgproc.cvtColor(img, gray, Imgproc.COLOR_BGR2GRAY);

        Size kSize = new Size(5,5);

        Imgproc.GaussianBlur(gray, gray, kSize, 0 );
        Imgproc.adaptiveThreshold(gray, thresh, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV, 9, 1);

        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(thresh, contours,new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);

        double area = 0;
        double peri = 0;
        MatOfPoint2f poly = new MatOfPoint2f();
        ArrayList<MatOfPoint2f> polyList = new ArrayList<>();

        for(MatOfPoint contour : contours){
            MatOfPoint2f curve = new MatOfPoint2f(contour);

            area = Imgproc.contourArea(contour);

            peri = Imgproc.arcLength(curve, true);

            Imgproc.approxPolyDP(curve, poly,0.01*peri, true);

            if(area > 100){
                polyList.add(poly);
            }
        }

        //int idx = 0
        ArrayList<Mat> mats = new ArrayList<>();

        for(MatOfPoint2f c : polyList){
            Mat mask = new Mat(src.rows(), src.cols(), src.type(), Scalar.all(0));
            Imgproc.drawContours(mask, (List<MatOfPoint>) c, -1, new Scalar(255,255,255), -1);

            Mat mat = new Mat();

            Core.bitwise_and(img, img, mat, mask);

            mats.add(mat);
        }


        return mats;
    }

    /*
    * Function which write image in path associated
    * If global image path : ./id_#id/modified_pieces/globalImage.png
    * else : ./id_#id/
    * dstImage is write here
    * */
    private ArrayList<String> save(){
        // path new images
        ArrayList<String> newImagesPath = new ArrayList<String>();

        String path;

        // if we run the global image
        if(globalImage) {
            path = "globalImage.png";
            File file = new File(pathSrc + "/modified_pieces/", path);
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Imgcodecs.imwrite(String.valueOf(file), dstImage);

            newImagesPath.add(String.valueOf(file));

            return newImagesPath;

        }
        // if we run pieces image
        else{
            for(Mat mat : piecesImage){
                int index = pathNewImages.indexOf(mat);
                path = "modified_piece_num"+index+".png";
                File file = new File(pathSrc + "/modified_pieces/", path);
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Imgcodecs.imwrite(String.valueOf(file), mat);

                newImagesPath.add(String.valueOf(file));
            }
            return newImagesPath;
        }
    }


}
