import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { AuthService } from "../auth.service";
import { AlertService } from "../../@shared/alert/alert.service";
import { User } from "../../@shared/user";
import { first } from "rxjs/operators";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;
  returnUrl: string;
  submitted = false;

  constructor(private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private authService: AuthService,
              private alertService: AlertService) {
  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
    this.returnUrl = "new-order";
  }
  get f() {
    return this.registerForm.controls;
  }

  onSubmit() {
    this.submitted = true;
    if (this.registerForm.invalid) {
      return;
    }
    const user = this.createUser();
    this.authService.register(user.username, user.password)
      .pipe(first())
      .subscribe(
        () => {
          this.authService.login(this.f.username.value,this.f.password.value).pipe(first()).subscribe(
            ()=>{
              this.router.navigate([this.returnUrl]);
            }
          )
        },
        error => {
          if (error.status === 403) {
            this.alertService.error(error.error.message);
          }
          if (error.status == 400) {
            this.alertService.error(error.error);
          }
        });
  }

  private createUser(): User {
    const user = new User();
    user.username = this.f.username.value;
    user.password = this.f.password.value;
    return user;
  }
}
