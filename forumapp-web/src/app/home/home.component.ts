import { Component, OnInit } from '@angular/core';

import { User } from '../_models/index';
import { UserService, AuthenticationService } from '../_services/index';

@Component({
    moduleId: module.id,
    templateUrl: 'home.component.html'
})

export class HomeComponent implements OnInit {
    currentUser: User;
    users: User[] = [];

    constructor(private userService: UserService,
                private authenticationService: AuthenticationService) {
        this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    }

    ngOnInit() {
        this.loadAllUsers();
    }

    deleteUser(id: number) {
        this.userService.delete(id).subscribe(() => {
          this.loadAllUsers();
          this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
        });
    }

    logout() {
      this.authenticationService.logout();
    }

    private loadAllUsers() {
        this.userService.getAll().subscribe(users => { this.users = users; });
    }
}
