import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Student } from '../student/student';
import { StudentService } from '../student/student.service';

@Component({
  selector: 'app-add-student',
  templateUrl: './add-student.component.html',
  styleUrls: ['./add-student.component.css']
})
export class AddStudentComponent implements OnInit {

  constructor(private studentService: StudentService) { }

  student : Student = new Student(10,"","");  
  submitted = false;  

  ngOnInit(): void {
    this.submitted = false; 
  }

  createStudent(): void {
    console.log("inside createStudent...");
    this.studentService.createStudent(this.student)
        .subscribe(data => {
          alert("student created successfully...");
        })
  }
}
