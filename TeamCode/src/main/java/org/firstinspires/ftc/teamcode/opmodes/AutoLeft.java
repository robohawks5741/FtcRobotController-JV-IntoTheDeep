package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous
public class AutoLeft extends LinearOpMode {
    ElapsedTime timer = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        Servo armBaFServo = hardwareMap.get(Servo.class, "armBaF");
        Servo armRotServo = hardwareMap.get(Servo.class, "armRot");
        Servo intakeServo = hardwareMap.get(Servo.class, "intake");
        Servo bucketServo = hardwareMap.get(Servo.class, "bucketArm");
        DcMotor armRaiseMotor = hardwareMap.get(DcMotor.class, "armRaise");
        DcMotor leftHangMotor = hardwareMap.get(DcMotor.class, "leftHang");
        DcMotor rightHangMotor = hardwareMap.get(DcMotor.class, "rightHang");
        armRaiseMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftHangMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightHangMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armBaFServo.setPosition(0.85);
        waitForStart();

        while(opModeIsActive()) {
            if(timer.seconds() < 3) {
                drive.setDrivePowers(new PoseVelocity2d(
                        new Vector2d(
                                0,
                                1
                        ),
                        0
                ));
            }
            leftHangMotor.setTargetPosition(0);
            rightHangMotor.setTargetPosition(0);
            leftHangMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightHangMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftHangMotor.setPower(0.05);
            rightHangMotor.setPower(0.05);
        }
    }
}
