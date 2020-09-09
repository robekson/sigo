import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFornece, Fornece } from 'app/shared/model/fornece.model';
import { ForneceService } from './fornece.service';
import { ForneceComponent } from './fornece.component';
import { ForneceDetailComponent } from './fornece-detail.component';
import { ForneceUpdateComponent } from './fornece-update.component';

@Injectable({ providedIn: 'root' })
export class ForneceResolve implements Resolve<IFornece> {
  constructor(private service: ForneceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFornece> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((fornece: HttpResponse<Fornece>) => {
          if (fornece.body) {
            return of(fornece.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Fornece());
  }
}

export const forneceRoute: Routes = [
  {
    path: '',
    component: ForneceComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigoApp.fornece.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ForneceDetailComponent,
    resolve: {
      fornece: ForneceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigoApp.fornece.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ForneceUpdateComponent,
    resolve: {
      fornece: ForneceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigoApp.fornece.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ForneceUpdateComponent,
    resolve: {
      fornece: ForneceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigoApp.fornece.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
