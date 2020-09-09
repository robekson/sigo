import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFuncionario } from 'app/shared/model/funcionario.model';

type EntityResponseType = HttpResponse<IFuncionario>;
type EntityArrayResponseType = HttpResponse<IFuncionario[]>;

@Injectable({ providedIn: 'root' })
export class FuncionarioService {
  public resourceUrl = SERVER_API_URL + 'api/funcionarios';

  constructor(protected http: HttpClient) {}

  create(funcionario: IFuncionario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(funcionario);
    return this.http
      .post<IFuncionario>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(funcionario: IFuncionario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(funcionario);
    return this.http
      .put<IFuncionario>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFuncionario>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFuncionario[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(funcionario: IFuncionario): IFuncionario {
    const copy: IFuncionario = Object.assign({}, funcionario, {
      dataNascimento:
        funcionario.dataNascimento && funcionario.dataNascimento.isValid() ? funcionario.dataNascimento.format(DATE_FORMAT) : undefined,
      dataAdmissao:
        funcionario.dataAdmissao && funcionario.dataAdmissao.isValid() ? funcionario.dataAdmissao.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataNascimento = res.body.dataNascimento ? moment(res.body.dataNascimento) : undefined;
      res.body.dataAdmissao = res.body.dataAdmissao ? moment(res.body.dataAdmissao) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((funcionario: IFuncionario) => {
        funcionario.dataNascimento = funcionario.dataNascimento ? moment(funcionario.dataNascimento) : undefined;
        funcionario.dataAdmissao = funcionario.dataAdmissao ? moment(funcionario.dataAdmissao) : undefined;
      });
    }
    return res;
  }
}
