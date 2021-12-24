import { Component, OnInit } from '@angular/core';
import { tap } from 'rxjs';
import { Employee } from '../employee/employee';
import { EmployeeService } from '../employee/employee.service';

@Component({
  selector: 'app-employees',
  templateUrl: './employees.component.html',
  styleUrls: ['./employees.component.css']
})
export class EmployeesComponent implements OnInit {

  employees: Employee[];

  employee: Employee = new Employee(1,"","",1);
  error: String;
  constructor(private employeeService: EmployeeService) { }

  ngOnInit(): void {
    this.employeeService.getEmployees()
      .subscribe(response => this.handleSuccessfulResponse(response),
                  error => this.handleErrorResponse(error));
  }

  handleErrorResponse(error){
    this.error = error;
  }

  handleSuccessfulResponse(response) {
    this.employees = response;
  }

  onDelete(id: number): any {
    return this.employeeService.deleteEmployee(id).pipe(tap())
      .subscribe((data:any) => {
        console.log(data);
        this.employees = this.employees.filter(e => e.id != id);
      })
  }

  onUpdate(emp:Employee, id: number): void {
    // emp.name = "Mr."+emp.name;
    this.employeeService.updateEmployee(id, emp)
        .subscribe(data => {alert('updated');this.employees.filter(e => this.updateEmployees(e))})
  }

  updateEmployees(e:Employee){
    for(let i=0; i<this.employees.length; i++){
      this.employee = this.employees[i];
      if(this.employee.id == e.id){
        this.employees[i] = e;
        break;
      }
    }
  }
}
