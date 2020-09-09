import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConsultoria } from 'app/shared/model/consultoria.model';

type EntityResponseType = HttpResponse<IConsultoria>;
type EntityArrayResponseType = HttpResponse<IConsultoria[]>;

@Injectable({ providedIn: 'root' })
export class ConsultoriaService {
  public resourceUrl = SERVER_API_URL + 'api/consultorias';

  constructor(protected http: HttpClient) {}

  create(consultoria: IConsultoria): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(consultoria);
    return this.http
      .post<IConsultoria>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(consultoria: IConsultoria): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(consultoria);
    return this.http
      .put<IConsultoria>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IConsultoria>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IConsultoria[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(consultoria: IConsultoria): IConsultoria {
    const copy: IConsultoria = Object.assign({}, consultoria, {
      dataContratacao:
        consultoria.dataContratacao && consultoria.dataContratacao.isValid() ? consultoria.dataContratacao.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((consultoria: IConsultoria) => {
        consultoria.dataContratacao = consultoria.dataContratacao ? moment(consultoria.dataContratacao) : undefined;
      });
    }
    return res;
  }
}
