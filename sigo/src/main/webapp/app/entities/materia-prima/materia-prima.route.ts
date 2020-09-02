import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMateriaPrima, MateriaPrima } from 'app/shared/model/materia-prima.model';
import { MateriaPrimaService } from './materia-prima.service';
import { MateriaPrimaComponent } from './materia-prima.component';
import { MateriaPrimaDetailComponent } from './materia-prima-detail.component';
import { MateriaPrimaUpdateComponent } from './materia-prima-update.component';

@Injectable({ providedIn: 'root' })
export class MateriaPrimaResolve implements Resolve<IMateriaPrima> {
  constructor(private service: MateriaPrimaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMateriaPrima> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((materiaPrima: HttpResponse<MateriaPrima>) => {
          if (materiaPrima.body) {
            return of(materiaPrima.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MateriaPrima());
  }
}

export const materiaPrimaRoute: Routes = [
  {
    path: '',
    component: MateriaPrimaComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigoApp.materiaPrima.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MateriaPrimaDetailComponent,
    resolve: {
      materiaPrima: MateriaPrimaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigoApp.materiaPrima.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MateriaPrimaUpdateComponent,
    resolve: {
      materiaPrima: MateriaPrimaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigoApp.materiaPrima.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MateriaPrimaUpdateComponent,
    resolve: {
      materiaPrima: MateriaPrimaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigoApp.materiaPrima.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
