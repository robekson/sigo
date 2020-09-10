import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipoProcesso, TipoProcesso } from 'app/shared/model/tipo-processo.model';
import { TipoProcessoService } from './tipo-processo.service';
import { TipoProcessoComponent } from './tipo-processo.component';
import { TipoProcessoDetailComponent } from './tipo-processo-detail.component';
import { TipoProcessoUpdateComponent } from './tipo-processo-update.component';

@Injectable({ providedIn: 'root' })
export class TipoProcessoResolve implements Resolve<ITipoProcesso> {
  constructor(private service: TipoProcessoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoProcesso> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipoProcesso: HttpResponse<TipoProcesso>) => {
          if (tipoProcesso.body) {
            return of(tipoProcesso.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoProcesso());
  }
}

export const tipoProcessoRoute: Routes = [
  {
    path: '',
    component: TipoProcessoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigoApp.tipoProcesso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TipoProcessoDetailComponent,
    resolve: {
      tipoProcesso: TipoProcessoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigoApp.tipoProcesso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TipoProcessoUpdateComponent,
    resolve: {
      tipoProcesso: TipoProcessoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigoApp.tipoProcesso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TipoProcessoUpdateComponent,
    resolve: {
      tipoProcesso: TipoProcessoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sigoApp.tipoProcesso.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
