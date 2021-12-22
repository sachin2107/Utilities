import { Component, OnInit } from '@angular/core';
import { Student } from '../student/student';
import { StudentService } from '../student/student.service';

@Component({
  selector: 'app-student-info',
  templateUrl: './student-info.component.html',
  styleUrls: ['./student-info.component.css']
})
export class StudentInfoComponent implements OnInit {

  student: Student;

  constructor(private studentService: StudentService) { }

  ngOnInit(): void {
    this.student = new Student(1, "", "");
  }

  viewStudentInfo(id: number) {
    console.log("in viewStudentInfo method...");
    return this.studentService.getStudent(id)
      .subscribe(response => {
        this.student = response;
      });
  }

}
