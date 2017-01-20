package org.usfirst.frc.team2729.robot.subsystems;

import org.usfirst.frc.team2729.robot.RobotMap;
import org.usfirst.frc.team2729.robot.commands.TankDrive;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {

	private final CANTalon _leftMain = new CANTalon(RobotMap.PORT_MOTOR_DRIVE_LEFT_MAIN),
			_left2 = new CANTalon(RobotMap.PORT_MOTOR_DRIVE_LEFT_2),
			_left3 = new CANTalon(RobotMap.PORT_MOTOR_DRIVE_LEFT_3),
			_rightMain = new CANTalon(RobotMap.PORT_MOTOR_DRIVE_RIGHT_MAIN),
			_right2 = new CANTalon(RobotMap.PORT_MOTOR_DRIVE_RIGHT_2),
			_right3 = new CANTalon(RobotMap.PORT_MOTOR_DRIVE_RIGHT_3);
	
	private final DoubleSolenoid _shifter = new DoubleSolenoid(RobotMap.PORT_SHIFT_DRIVE_HIGH, RobotMap.PORT_SHIFT_DRIVE_LOW);
	
	private boolean _isHighGear = false;
	private boolean _halfOne = false, _halfTwo = false;
//	private boolean _isPTOEnabled = false;

			
	public DriveTrain(){
		_shifter.set(DoubleSolenoid.Value.kForward);
		_isHighGear = false;
		_left2.changeControlMode(CANTalon.TalonControlMode.Follower);
		_left2.set(_leftMain.getDeviceID());
		_left3.changeControlMode(CANTalon.TalonControlMode.Follower);
		_left3.set(_leftMain.getDeviceID());
		_right2.changeControlMode(CANTalon.TalonControlMode.Follower);
		_right2.set(_rightMain.getDeviceID());
		_right3.changeControlMode(CANTalon.TalonControlMode.Follower);
		_right3.set(_rightMain.getDeviceID());
		_leftMain.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		_rightMain.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		resetLeftEnc();
		resetRightEnc();
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new TankDrive());
	}

	public void halveOne(boolean half){
		_halfOne = half;
	}

	public void halveTwo(boolean half){
		_halfTwo = half;
	}

	public void halt() {
		_leftMain.set(0);
		_rightMain.set(0);
	}

	public void tankDrive(double left, double right){
		_leftMain.set(-((left) - (_halfOne ? (left/3) : 0) - (_halfTwo ? (left/3) : 0)));
		_rightMain.set((right) - (_halfOne ? (right/3) : 0) - (_halfTwo ? (right/3) : 0));
	}

	public double getLeftDistance(){
		return _leftMain.getEncPosition();
	}

	public double getRightDistance(){
		return -_rightMain.getEncPosition();
	}

	public double getLeftSpeedEnc() {
		return _leftMain.getEncVelocity();
	}
	
	public double getRightSpeedEnc() {
		return _rightMain.getEncVelocity();
	}
	
	public void resetLeftEnc() {
		_leftMain.setEncPosition(0);
	}

	public void resetRightEnc() {
		_rightMain.setEncPosition(0);
	}

	public void setHighGear(boolean enabled) {
		_isHighGear  = enabled;
		_shifter.set(enabled ? DoubleSolenoid.Value.kReverse
				: DoubleSolenoid.Value.kForward);
	}
	
	public boolean getHighGear(){
		return _isHighGear;
	}

//	public boolean getPTO(){
//		return _isPTOEnabled;
//	}
}