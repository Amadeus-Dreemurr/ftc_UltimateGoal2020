
/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="ultimateGoalTeleOP", group="MyCode")

public class ultimateGoalTeleOP extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFrontDrive = null;
    private DcMotor leftRearDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightRearDrive = null;
    private DcMotor inputDrive = null;
    private DcMotor outputDrive = null;
    private DcMotor transportDrive1 = null;
    private DcMotor transportDrive2 = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftFrontDrive  = hardwareMap.get(DcMotor.class, "left_front_drive");
        leftRearDrive = hardwareMap.get(DcMotor.class, "left_rear_drive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");
        rightRearDrive = hardwareMap.get(DcMotor.class, "right_rear_drive");
        inputDrive = hardwareMap.get(DcMotor.class, "input_drive");
        outputDrive = hardwareMap.get(DcMotor.class, "output_drive");
        transportDrive1 = hardwareMap.get(DcMotor.class, "transport_drive_1");
        transportDrive2 = hardwareMap.get(DcMotor.class, "transport_drive_2");


        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftRearDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightRearDrive.setDirection(DcMotor.Direction.REVERSE);
        inputDrive.setDirection(DcMotor.Direction.FORWARD);
        outputDrive.setDirection(DcMotor.Direction.FORWARD);
        transportDrive1.setDirection(DcMotor.Direction.FORWARD);
        transportDrive2.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // Set power to the mecanum wheels

            // translation: the magnitude of the translational motion is achieved
            // by calculation the magnitude of the vector comprised of a x-directional
            // translational motion and a y-directional translational motion. In other
            // words, the magnitude of the net translational motion is the length of
            // the hypotenuse of a right triangle with leg of length of the magnitude
            // of the x-directional motion and the y-directional motion respectively
            double translation = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);

            // translationAngle: the angle(theta) to the direction in which the robot
            // travels is calculated by subtracting pi/4 from the angle to the direction
            // of the positive x-axis which is the inverse tangent of the quotient of
            // y-directional motion and x-directional motion
            double tranlationAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI/4;

            // rotation: this is a constant which is modified to every wheel to turn the
            // robot while driving
            double rotation = gamepad1.right_stick_x;

            // Calculate the power at each wheel
            final double v1 = translation*Math.cos(tranlationAngle)+rotation;
            final double v2 = translation*Math.sin(tranlationAngle)-rotation;
            final double v3 = translation*Math.sin(tranlationAngle)+rotation;
            final double v4 = translation*Math.cos(tranlationAngle)-rotation;

            // Send the calculated power to wheel
            leftFrontDrive.setPower(v1);
            leftRearDrive.setPower(v2);
            rightFrontDrive.setPower(v3);
            rightRearDrive.setPower(v4);

            // Show the elapsed game time and wheel power.
            //telemetry.addData("Status", "Run Time: " + runtime.toString());
            //telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }
    }
}
