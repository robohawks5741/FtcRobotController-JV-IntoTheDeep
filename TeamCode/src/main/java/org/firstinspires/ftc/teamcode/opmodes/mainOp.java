package org.firstinspires.ftc.teamcode.opmodes;

import android.graphics.Path;
import android.util.Size;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

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
        Servo armBaFServo = hardwareMap.get(Servo.class, "armBaF");
        Servo armRotServo = hardwareMap.get(Servo.class, "armRot");
        Servo intakeServo = hardwareMap.get(Servo.class, "intake");
        Servo bucketServo = hardwareMap.get(Servo.class, "bucketArm");
        DcMotor armRaiseMotor = hardwareMap.get(DcMotor.class, "armRaise");
        DcMotor leftHangMotor = hardwareMap.get(DcMotor.class, "leftHang");
        DcMotor rightHangMotor = hardwareMap.get(DcMotor.class, "rightHang");

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
                    rightHangMotor.setTargetPosition(10);
                }

                if (gamepad1.left_trigger > 0.5f) {
                    leftHangMotor.setTargetPosition(0);
                    rightHangMotor.setTargetPosition(0);
                }

                if (gamepad1.right_stick_y > 0.8f) {
                    armRaiseMotor.setTargetPosition(10);
                }

                if (gamepad1.right_stick_y < -0.8f) {
                    armRaiseMotor.setTargetPosition(0);
                }

                if (gamepad1.left_bumper) intakeServo.setPosition(1.0f);
                if (gamepad1.left_trigger > 0.8f) intakeServo.setPosition(0.0f);
                if (gamepad1.dpad_up) bucketServo.setPosition(bucketServo.getPosition() + 0.01f);
                if (gamepad1.dpad_down) bucketServo.setPosition(bucketServo.getPosition() - 0.01f);
                if (gamepad1.dpad_left) armBaFServo.setPosition(armBaFServo.getPosition() - 0.01f);
                if (gamepad1.dpad_right) armBaFServo.setPosition(armBaFServo.getPosition() + 0.01f);
                if (gamepad1.a) armRotServo.setPosition(armRotServo.getPosition() - 0.01f);
                if (gamepad1.y) armRotServo.setPosition(armRotServo.getPosition() + 0.01f);

                telemetry.update();
            }
        }
    }
}
