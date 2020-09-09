import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INormas, Normas } from 'app/shared/model/normas.model';
import { NormasService } from './normas.service';
import { NormasComponent } from './normas.component';
import { NormasDetailComponent } from './normas-detail.component';
import { NormasUpdateComponent } from './normas-update.component';

@Injectable({ providedIn: 'root' })
export class NormasResolve implements Resolve<INormas> {
  constructor(private service: NormasService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INormas> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((normas: HttpResponse<Normas>) => {
          if (normas.body) {
            return of(normas.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Normas());
  }
}

export const normasRoute: Routes = [
  {
    path: '',
    component: NormasComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'sigoApp.normas.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NormasDetailComponent,
    resolve: {
      normas: NormasResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigoApp.normas.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NormasUpdateComponent,
    resolve: {
      normas: NormasResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigoApp.normas.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NormasUpdateComponent,
    resolve: {
      normas: NormasResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigoApp.normas.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
