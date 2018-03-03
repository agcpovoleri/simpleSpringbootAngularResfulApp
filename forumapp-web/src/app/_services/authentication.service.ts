import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Router, ActivatedRoute} from '@angular/router';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

@Injectable()
export class AuthenticationService {
    constructor(private http: HttpClient,
                private route: ActivatedRoute,
                private router: Router,
                ) { }

    login(username: string, password: string) {
        return this.http.post<any>('/api/auth/login', { login: username, password: password })
            .map(user => {
                // login successful if there's a jwt token in the response
                if (user && user.authSessionKey) {
                    // store user details and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentUser', JSON.stringify(user));
                }

                return user;
            });
    }

    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
        this.router.navigate(['login']);
    }
}
