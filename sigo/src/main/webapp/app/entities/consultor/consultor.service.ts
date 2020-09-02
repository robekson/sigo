import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConsultor } from 'app/shared/model/consultor.model';

type EntityResponseType = HttpResponse<IConsultor>;
type EntityArrayResponseType = HttpResponse<IConsultor[]>;

@Injectable({ providedIn: 'root' })
export class ConsultorService {
  public resourceUrl = SERVER_API_URL + 'api/consultors';

  constructor(protected http: HttpClient) {}

  create(consultor: IConsultor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(consultor);
    return this.http
      .post<IConsultor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(consultor: IConsultor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(consultor);
    return this.http
      .put<IConsultor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IConsultor>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IConsultor[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(consultor: IConsultor): IConsultor {
    const copy: IConsultor = Object.assign({}, consultor, {
      dataContratacao:
        consultor.dataContratacao && consultor.dataContratacao.isValid() ? consultor.dataContratacao.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataContratacao = res.body.dataContratacao ? moment(res.body.dataContratacao) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((consultor: IConsultor) => {
        consultor.dataContratacao = consultor.dataContratacao ? moment(consultor.dataContratacao) : undefined;
      });
    }
    return res;
  }
}
