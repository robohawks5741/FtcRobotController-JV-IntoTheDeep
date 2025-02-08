package org.firstinspires.ftc.teamcode.opmodes;

import android.graphics.Path;
import android.util.Size;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Servo.Direction;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "mainOpMode")

public class mainOp extends LinearOpMode {
    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        //these two are currently switched, this can be fixed on the hardware map pretty quickly when
        //the robot is accessible
        Servo armBaFServo = hardwareMap.get(Servo.class, "armBaF");
        Servo armRotServo = hardwareMap.get(Servo.class, "armRot");


        Servo intakeServo = hardwareMap.get(Servo.class, "intake");
        Servo bucketServo = hardwareMap.get(Servo.class, "bucketArm");
        DcMotor armRaiseMotor = hardwareMap.get(DcMotor.class, "armRaise");
        DcMotor leftHangMotor = hardwareMap.get(DcMotor.class, "leftHang");
        DcMotor rightHangMotor = hardwareMap.get(DcMotor.class, "rightHang");
        armRaiseMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftHangMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightHangMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRaiseMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftHangMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightHangMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armBaFServo.setPosition(0.85);
        boolean hanging = false;
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                drive.setDrivePowers(new PoseVelocity2d(
                        new Vector2d(
                                gamepad1.right_stick_x,
                                gamepad1.left_stick_x
                        ),
                        gamepad1.left_stick_y
                ));
                drive.updatePoseEstimate();

                if (gamepad1.left_bumper) {
                    leftHangMotor.setTargetPosition(10);
                    rightHangMotor.setTargetPosition(-10);
                    leftHangMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rightHangMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    leftHangMotor.setPower(0.2);
                    rightHangMotor.setPower(0.2);
                    hanging = true;
                }

                else if (gamepad1.left_trigger > 0.5) {
                    leftHangMotor.setTargetPosition(0);
                    rightHangMotor.setTargetPosition(0);
                    leftHangMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rightHangMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    leftHangMotor.setPower(0.2);
                    rightHangMotor.setPower(0.2);
                    hanging = false;
                }

                else if (!hanging){
                    leftHangMotor.setTargetPosition(0);
                    rightHangMotor.setTargetPosition(0);
                    leftHangMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rightHangMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    leftHangMotor.setPower(0.05);
                    rightHangMotor.setPower(0.05);
                }

                if (gamepad1.right_stick_y > 0.8) {
                    armRaiseMotor.setTargetPosition(-5);
                    armRaiseMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    armRaiseMotor.setPower(0.1);
                }

                if (gamepad1.right_stick_y < -0.8) {
                    armRaiseMotor.setTargetPosition(0);
                    armRaiseMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    armRaiseMotor.setPower(0.1);
                }

                if (gamepad1.right_bumper) intakeServo.setPosition(intakeServo.getPosition() - 0.01);
                if (gamepad1.right_trigger > 0.8) intakeServo.setPosition(intakeServo.getPosition() + 0.01);
                if (gamepad1.dpad_up) bucketServo.setPosition(bucketServo.getPosition() - 0.01);
                if (gamepad1.dpad_down) bucketServo.setPosition(bucketServo.getPosition() + 0.01);
                if (gamepad1.dpad_left) armBaFServo.setPosition(armBaFServo.getPosition() + .01);
                if (gamepad1.dpad_right) armBaFServo.setPosition(armBaFServo.getPosition() - .01);
                if (gamepad1.a) armRotServo.setPosition(0);
                if (gamepad1.y) armRotServo.setPosition(1);
                telemetry.addData("Hang Pos", leftHangMotor.getCurrentPosition());
                telemetry.addData("servo", armBaFServo.getPosition());
                telemetry.update();
            }
        }
    }
}
