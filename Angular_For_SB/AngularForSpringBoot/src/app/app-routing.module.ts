import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddStudentComponent } from './add-student/add-student.component';
import { StudentInfoComponent } from './student-info/student-info.component';
import { StudentListComponent } from './student-list/student-list.component';

const routes: Routes = [
  { path: '', redirectTo: 'students', pathMatch: 'full' },
  { path: 'students', component: StudentListComponent },  
  { path: 'addstudent', component: AddStudentComponent },
  { path: 'student', component: StudentInfoComponent}  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
